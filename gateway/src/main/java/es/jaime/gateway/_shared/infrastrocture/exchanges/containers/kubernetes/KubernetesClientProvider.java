package es.jaime.gateway._shared.infrastrocture.exchanges.containers.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@Component
public class KubernetesClientProvider {
    @Bean
    @SneakyThrows
    public CoreV1Api coreV1Api(){
        ApiClient client = ClientBuilder.cluster().build();
        Configuration.setDefaultApiClient(client);

        return new CoreV1Api(Config.defaultClient());
    }
}
