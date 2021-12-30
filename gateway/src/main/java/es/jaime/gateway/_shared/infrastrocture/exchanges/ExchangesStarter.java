package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.ordertypes.domain.OrderType;
import es.jaime.gateway.ordertypes.domain.OrderTypeName;
import es.jaime.gateway.ordertypes.domain.OrderTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@DependsOn({"rabbitmq-configuration", "rabbitmq-starter"})
public class ExchangesStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;
    private final OrderTypeRepository orderTypes;

    public ExchangesStarter(ListedCompaniesRepository repository, OrderTypeRepository orderTypes) {
        this.repository = repository;
        this.orderTypes = orderTypes;
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
        List<String> queueNames = queuesThatExchangeWillListen(listedCompany);
        List<OrderType> allOrderTypes = orderTypes.findAll();
        List<String> toReturn = new ArrayList<>();

        for (int i = 0; i < allOrderTypes.size(); i++) {
            toReturn.add(String.format("--%s=%s",
                    allOrderTypes.get(i).getName().value(),
                    queueNames.get(i)));
        }

        return toReturn;
    }

    private List<String> queuesThatExchangeWillListen(ListedCompany listedCompany){
        return orderTypes.findAll().stream()
                .map(orderType -> RabbitMQNameFormatter.newOrdersQueue(orderType, listedCompany))
                .collect(Collectors.toList());
    }
}
