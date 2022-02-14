package es.jaime.gateway._shared.domain.configuration;

import java.util.Map;
import java.util.Set;

public interface ConfigurationFileMapper {
    Map<String, Object> getConfigMap();
    Set<String> getUsingConfigsNames();
}
