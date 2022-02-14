package es.jaime.gateway._shared.infrastrocture.orchestration.dockercompose;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "orchestration", havingValue = "docker-compose")
@AllArgsConstructor
@Order(100)
public final class DockerClientProvider {
    private final ApplicationConfiguration configuration;

    @Bean
    public DockerClient dockerClient(){
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(configuration.get("DOCKER_HOST")).build();

        return DockerClientBuilder.getInstance(config).build();
    }
}
