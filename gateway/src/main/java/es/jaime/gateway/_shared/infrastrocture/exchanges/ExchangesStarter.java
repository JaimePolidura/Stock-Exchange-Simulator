package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
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
@DependsOn({"rabbitmq-configuration", "rabbitmq-starter"})
@AllArgsConstructor
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;
    private final DockerClient dockerClient;
    private final EventBus eventBus;

    @Override
    public void run(String... args) {
        Map<ListedCompany, String> exchangesContainers = new HashMap<>();
        List<ListedCompany> listedCompanies = repository.findAll();

        for (ListedCompany listedCompany : listedCompanies) {
            String containerId = startDockerContainer(dockerClient, listedCompany);

            exchangesContainers.put(listedCompany, containerId);
        }
    }

    private String startDockerContainer(DockerClient dockerClient, ListedCompany listedCompany){
        String containerID = dockerClient.createContainerCmd("sxs-exchange")
                .withCmd(cmdToExchange(listedCompany))
                .withRestartPolicy(RestartPolicy.unlessStoppedRestart())
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode("stock-exchange-simulator_default"))
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
                "1000",
                listedCompany.ticker().value()
        );
    }
}
