package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Configuration
@DependsOn({"rabbitmq-configuration", "rabbitmq-starter", "database-configuration"})
@AllArgsConstructor
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;
    private final DockerClient dockerClient;
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) {
        Map<ListedCompany, String> exchangesContainers = new HashMap<>();
        List<ListedCompany> listedCompanies = repository.findAll();

        removeExchangesContainers();

        for (ListedCompany listedCompany : listedCompanies) {
            String containerId = startDockerContainer(dockerClient, listedCompany);

            exchangesContainers.put(listedCompany, containerId);
        }
    }

    private void removeExchangesContainers(){
        dockerClient.listContainersCmd().exec().stream()
                .filter(container -> container.getImage().equalsIgnoreCase(configuration.get("DOCKER_EXCHANGE_IMAGE")))
                .map(Container::getId)
                .forEach(id -> dockerClient.stopContainerCmd(id).exec());
    }

    private String startDockerContainer(DockerClient dockerClient, ListedCompany listedCompany){
        String containerID = dockerClient.createContainerCmd(configuration.get("DOCKER_EXCHANGE_IMAGE"))
                .withCmd(cmdToExchange(listedCompany))
                .withRestartPolicy(RestartPolicy.unlessStoppedRestart())
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode(configuration.get("DOCKER_NETWORK")))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerID)
                .exec();

        return containerID;
    }

    private List<String> cmdToExchange(ListedCompany listedCompany){
        return List.of(
                RabbitMQNameFormatter.newOrdersQueueName(listedCompany.ticker()),
                RabbitMQNameFormatter.EVENTS_EXCHANGE,
                configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                listedCompany.ticker().value()
        );
    }
}
