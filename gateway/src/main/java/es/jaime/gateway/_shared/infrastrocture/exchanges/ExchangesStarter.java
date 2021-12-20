package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
@DependsOn({"rabbitmq-configuration"})
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;

    public ExchangesStarter(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
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
        System.out.println("HOLA");

        String containerID = dockerClient.createContainerCmd("sxs-exchange")
                .withCmd("--queue=sxs.new-orders." + listedCompany.ticker().value())
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode("stock-exchange-simulator_default"))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerID)
                .exec();
    }
}
