package es.jaime.gateway._shared.infrastrocture.exchanges.containers.dockercompose;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.exchange.ExchangeDescriptor;
import es.jaime.gateway._shared.domain.exchange.ExchangeContainerApiService;
import io.kubernetes.client.openapi.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("containerservice-docker-compose")
@ConditionalOnProperty(value = "orchestration", havingValue = "docker-compose")
@AllArgsConstructor
public class DockerComposeService implements ExchangeContainerApiService {
    private final DockerClient dockerClient;
    private final ExchangeDescriptor exchangeDescriptor;
    private final ApplicationConfiguration configuration;

    @Override
    public void deleteContainerByIdentifier(String id) throws ApiException {
        this.dockerClient.killContainerCmd(id).exec();
        this.dockerClient.stopContainerCmd(id).exec();
        this.dockerClient.removeContainerCmd(id).exec();
    }

    @Override
    public List<String> findContainerIdentifiersNamesByImage(String image) {
        return this.dockerClient.listContainersCmd().exec().stream()
                .map(Container::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void startContainer(String ticker) {
        String exchangeName = exchangeDescriptor.name(ticker);

        String containerID = dockerClient.createContainerCmd(configuration.get("DOCKER_EXCHANGE_IMAGE"))
                .withName(exchangeName)
                .withRestartPolicy(RestartPolicy.onFailureRestart(100))
                .withEnv(environtmentVariables(ticker, exchangeName))
                .withHostConfig(HostConfig
                        .newHostConfig()
                        .withNetworkMode(configuration.get("DOCKER_NETWORK")))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerID)
                .exec();
    }

    private List<String> environtmentVariables(String ticker, String exchangeName){
        Map<String, Object> environtmentVariablesInMap = exchangeDescriptor.properties(ticker, exchangeName);
        List<String> environtmentVariablesInList = new ArrayList<>();

        environtmentVariablesInMap.forEach((k, v) -> {
            environtmentVariablesInList.add(String.format("%s=%s", k, v));
        });

        return environtmentVariablesInList;

    }
}
