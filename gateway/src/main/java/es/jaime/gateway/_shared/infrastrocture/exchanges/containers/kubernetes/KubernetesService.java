package es.jaime.gateway._shared.infrastrocture.exchanges.containers.kubernetes;

import es.jaime.gateway._shared.domain.exchange.ExchangeDescriptor;
import es.jaime.gateway._shared.domain.exchange.ExchangeContainerApiService;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("containerservice-kubernetes")
@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@AllArgsConstructor
public class KubernetesService implements ExchangeContainerApiService {
    private CoreV1Api kubernetesApi;
    private ExchangeDescriptor exchangeDescriptor;
    private ExchangePodBuilder exchangePodBuilder;

    @Override
    public void deleteContainerByIdentifier(String name) throws ApiException {
        this.kubernetesApi.deleteNamespacedPod(name, "default", null, null, null, null, null, null);
    }

    @SneakyThrows
    @Override
    public List<String> findContainerIdentifiersNamesByImage(String image) {
        return this.kubernetesApi.listNamespacedPod("default", null, null, null, null, null, null, null, null, null, null)
                .getItems()
                .stream()
                .filter(pod -> sameImage(pod, image))
                .map(pod -> pod.getSpec().getContainers().get(0).getName())
                .collect(Collectors.toList());
    }

    private boolean sameImage(V1Pod pod, String image){
        return pod.getSpec().getContainers().stream()
                .anyMatch(v1Container -> v1Container.getImage().equalsIgnoreCase(image));
    }

    @SneakyThrows
    @Override
    public void startContainer(String ticker) {
        String exchangeName = exchangeDescriptor.name(ticker);

        V1Pod pod = exchangePodBuilder.build(ticker, exchangeDescriptor.properties(ticker, exchangeName), exchangeName);

        kubernetesApi.createNamespacedPod("default", pod, null, null, null);
    }
}
