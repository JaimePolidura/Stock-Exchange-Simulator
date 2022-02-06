package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Configuration
@AllArgsConstructor
@Order(2)
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository listedCompanies;
    private final DockerClient dockerClient;
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) {
        Map<ListedCompany, String> exchangesContainers = new HashMap<>();
        List<String> allTickersExchangeNames = this.getTickers();
        List<String> tickersOfExchangesNotStarted = tickersOfExchangesNotStarted(allTickersExchangeNames);

        startContainers(tickersOfExchangesNotStarted);
    }

    private void startContainers(List<String> tickersToStart){
        Executors.newCachedThreadPool().submit(() -> {
            try {
                //By this we make sure that all queues have been initializedd
                Thread.sleep(10000);

                for (String tickerOfExchangeNotStarted : tickersToStart) {
                    startDockerContainer(tickerOfExchangeNotStarted);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> tickersOfExchangesNotStarted(List<String> tickers){
        List<String> containerExchangeNames = tickers.stream()
                .map(ExchangesStarter::nameForExchangeContainer)
                .collect(Collectors.toList());

        List<Container> exchangesRunning = dockerClient.listContainersCmd().withNameFilter(containerExchangeNames).exec();
        List<String> tickersOfExchangesToStart = new ArrayList<>();

        for (String exchangeName : containerExchangeNames) {
            if(created(exchangesRunning, exchangeName) && running(exchangesRunning, exchangeName)){
                continue;
            }

            if(created(exchangesRunning, exchangeName) && !running(exchangesRunning, exchangeName)){
                removeContainer(exchangesRunning, exchangeName);
            }

            tickersOfExchangesToStart.add(tickerFromExchangeName(exchangeName));
        }

        return tickersOfExchangesToStart;
    }

    private String tickerFromExchangeName(String exchangeName){
        return exchangeName.split("-")[1];
    }

    private boolean created(List<Container> exchangesRunning, String ticker){
        return !notCreated(exchangesRunning, ticker);
    }

    private boolean notCreated(List<Container> exchangesRunning, String ticker) {
        return exchangesRunning.stream()
                .map(this::getNameFromContainer)
                .noneMatch(c -> c.equalsIgnoreCase(ticker));
    }

    private boolean running(List<Container> exchangesRunning, String ticker) {
        Optional<Container> container = exchangesRunning.stream()
                .filter(c -> c.getNames()[0].equalsIgnoreCase(ticker))
                .findFirst();

        return container.isPresent() && container.get().getState().equalsIgnoreCase("running");
    }

    private void removeContainer(List<Container> containers, String ticker){
        Container containerToRemove = containers.stream()
                .filter(container -> sameName(container, ticker))
                .findFirst()
                .get();

        dockerClient.removeContainerCmd(containerToRemove.getId());
        dockerClient.stopContainerCmd(containerToRemove.getId());
        dockerClient.killContainerCmd(containerToRemove.getId());
    }

    private boolean sameName(Container container, String expectedName){
        return getNameFromContainer(container).equalsIgnoreCase(expectedName);
    }

    private String getNameFromContainer(Container container){
        return container.getNames()[0];
    }

    private List<String> getTickers(){
        return this.listedCompanies.findAll().stream()
                .map(l -> l.ticker().value())
                .collect(Collectors.toList());
    }

    private void startDockerContainer(String ticker){
        String containerID = dockerClient.createContainerCmd(configuration.get("DOCKER_EXCHANGE_IMAGE"))
                .withCmd(cmdToExchange(ticker))
                .withName(nameForExchangeContainer(ticker))
                .withRestartPolicy(RestartPolicy.onFailureRestart(100))
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode(configuration.get("DOCKER_NETWORK")))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerID)
                .exec();

    }

    private List<String> cmdToExchange(String ticker){
        return List.of(
                RabbitMQNameFormatter.newOrdersQueueName(ListedCompanyTicker.of(ticker)),
                RabbitMQNameFormatter.EVENTS_EXCHANGE,
                configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                ticker,
                configuration.get("EXCHANGE_INITIAL_DELAY")
        );
    }

    public static String nameForExchangeContainer(String ticker){
        return format("exchange-%s", ticker);
    }
}
