package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.Utils;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Configuration
@ConditionalOnProperty(value = "orchestration", havingValue = "docker-compose")
@Order(50)
@AllArgsConstructor
public class ExchangesStarterDockerCompose implements CommandLineRunner {
    private final ListedCompaniesRepository listedCompanies;
    private final DockerClient dockerClient;
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) {
        Executors.newCachedThreadPool().submit(() -> {
            this.removeExistingRunningExchanges();
            this.startExchanges(this.getTickers());
        });
    }

    private void removeExistingRunningExchanges(){
        this.dockerClient.listContainersCmd().exec().stream()
                .filter(this::isExchangeContainer)
                .forEach(this::killContainer);
    }

    private boolean isExchangeContainer(Container container){
        return container.getImage().equalsIgnoreCase(this.configuration.get("DOCKER_EXCHANGE_IMAGE"));
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

    private void startExchanges(List<String> tickersToStart){
        //By this we make sure that all queues have been initializedd
        Utils.sleep(10000);
        int exchangeReplicas = configuration.getInt("EXCHANGE_REPLICAS");

        for (String tickerOfExchangeNotStarted : tickersToStart) {
            for (int i = 0; i < exchangeReplicas; i++) {
                startDockerContainer(tickerOfExchangeNotStarted);
            }
        }
    }

    private void startDockerContainer(String ticker){
        String nameForTheExchange = nameForSpecificExchangeOfTicker(ticker);

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
                exchangeName,
                configuration.get("REDIS_HOST"),
                configuration.get("REDIS_PORT"),
                configuration.get("REDIS_PASSWORD")
        );
    }

    public static String nameForSpecificExchangeOfTicker(String ticker){
        return format("exchange-%s-%s", ticker, UUID.randomUUID());
    }

    public static String nameForExchangesOfTicker(String ticker){
        return format("exchange-%s", ticker);
    }
}
