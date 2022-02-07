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
        this.removeExchanges();
        this.startContainers(this.getTickers());
    }

    private void removeExchanges(){
        this.dockerClient.listContainersCmd().exec().stream()
                .filter(this::isExchangeContainer)
                .forEach(this::killContainer);
    }

    private boolean isExchangeContainer(Container container){
        return container.getImage().equalsIgnoreCase(this.configuration.get("EXCHANGE_CONTAINER_IMAGE_NAME"));
    }

    private void killContainer(Container container){
        this.dockerClient.killContainerCmd(container.getId()).exec();
        this.dockerClient.stopContainerCmd(container.getId()).exec();
        this.dockerClient.removeContainerCmd(container.getId()).exec();
    }

    private List<String> getTickers(){
        return this.listedCompanies.findAll().stream()
                .map(l -> l.ticker().value())
                .collect(Collectors.toList());
    }

    private void startContainers(List<String> tickersToStart){
        Executors.newCachedThreadPool().submit(() -> {
            try {
                //By this we make sure that all queues have been initializedd
                Thread.sleep(10000);
                var exchangeReplicas = this.configuration.getInt("EXCHANGE_REPLICAS");

                for (String tickerOfExchangeNotStarted : tickersToStart) {
                    for (int i = 0; i < exchangeReplicas; i++) {
                        startDockerContainer(tickerOfExchangeNotStarted);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void startDockerContainer(String ticker){
        String nameForTheExchange = nameForExchangeContainer(ticker);

        String containerID = dockerClient.createContainerCmd(configuration.get("DOCKER_EXCHANGE_IMAGE"))
                .withCmd(cmdToExchange(ticker, nameForTheExchange))
                .withName(nameForTheExchange)
                .withRestartPolicy(RestartPolicy.onFailureRestart(100))
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode(configuration.get("DOCKER_NETWORK")))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerID)
                .exec();
    }

    private List<String> cmdToExchange(String ticker, String exchangeName){
        return List.of(
                RabbitMQNameFormatter.newOrdersQueueName(ticker),
                RabbitMQNameFormatter.EVENTS_EXCHANGE,
                configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                ticker,
                configuration.get("EXCHANGE_INITIAL_DELAY"),
                RabbitMQNameFormatter.EVENTS_ROUTING_KEY,
                exchangeName
        );
    }

    public static String nameForExchangeContainer(String ticker){
        return format("exchange-%s-%s", ticker, UUID.randomUUID());
    }
}
