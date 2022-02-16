package es.jaime.gateway._shared.infrastrocture.exchanges.starter.kubernetes;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import io.kubernetes.client.openapi.models.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@Service
@AllArgsConstructor
public class ExchangePodBuilder {
    private final ApplicationConfiguration configuration;

    public V1Pod build(String ticker, Map<String, Object> args){
        String exchangeName = ExchangesStarterKubernetes.nameForExchange(ticker);

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setLabels(Map.of("app", exchangeName));
        meta.setName(exchangeName);

        V1Container container = new V1Container();
        container.setImage(configuration.get("DOCKER_EXCHANGE_IMAGE"));
        container.setName(exchangeName);
        container.setEnv(this.environmentsVariablesFrom(args));

        V1PodSpec v1PodSpec = new V1PodSpec();
        v1PodSpec.setRestartPolicy("Always");
        v1PodSpec.setContainers(List.of(container));

        V1Pod v1Pod = new V1Pod();
        v1Pod.setApiVersion("v1");
        v1Pod.setKind("Pod");
        v1Pod.setMetadata(meta);
        v1Pod.setSpec(v1PodSpec);

        return v1Pod;
    }

    private List<V1EnvVar> environmentsVariablesFrom(Map<String, Object> args){
        return args.entrySet().stream()
                .map(this::environmentVariableFromEntry)
                .collect(Collectors.toList());
    }

    private V1EnvVar environmentVariableFromEntry(Map.Entry<String, Object> entry){
        var environtmentVariable = new V1EnvVar();
        environtmentVariable.setName(entry.getKey());
        environtmentVariable.setValue((String) entry.getValue());

        return environtmentVariable;
    }
}
