package es.jaime.gateway._shared.infrastrocture.orchestration.kubernetes;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
public class KubernetesPodService {
    private final CoreV1Api kubernetesApi;

    public void deletePodByName(String podName) throws ApiException {
        this.kubernetesApi.deleteNamespacedPod(podName, "default", null, null, null, null, null, null);
    }

    public List<V1Pod> listPods() throws ApiException {
        return this.kubernetesApi.listNamespacedPod("default", null, null, null, null, null, null, null, null, null, null)
                .getItems();
    }

    @SneakyThrows
    public void createPod(V1Pod pod) {
        kubernetesApi.createNamespacedPod("default", pod, null, null, null);
    }

    public boolean sameImage(V1Pod pod, String image){
        return pod.getSpec().getContainers().stream()
                .anyMatch(v1Container -> v1Container.getImage().equalsIgnoreCase(image));
    }

}
