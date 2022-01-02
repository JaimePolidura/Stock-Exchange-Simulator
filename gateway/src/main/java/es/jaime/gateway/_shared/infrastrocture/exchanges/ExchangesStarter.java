package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQDeclarables;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQDeclarables.*;

@Configuration
@DependsOn({"rabbitmq-configuration", "rabbitmq-starter"})
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;

    public ExchangesStarter(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        List<ListedCompany> listedCompanies = repository.findAll();
        DockerClient dockerClient = startDockerConnection();

        for (ListedCompany listedCompany : listedCompanies) {
            startDockerContainer(dockerClient, listedCompany);
        }
    }

    private DockerClient startDockerConnection(){
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://host.docker.internal:2375").build();

        return DockerClientBuilder.getInstance(config).build();
    }

    private void startDockerContainer(DockerClient dockerClient, ListedCompany listedCompany){
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
    }

    private List<String> cmdToExchange(ListedCompany listedCompany){
        return List.of(
                RabbitMQNameFormatter.newOrdersQueueName(listedCompany.ticker()),
                RabbitMQNameFormatter.EVENTS_EXCHANGE,
                "100"
        );
    }
}
