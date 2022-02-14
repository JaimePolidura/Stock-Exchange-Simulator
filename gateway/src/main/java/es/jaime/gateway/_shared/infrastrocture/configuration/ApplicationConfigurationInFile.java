package es.jaime.gateway._shared.infrastrocture.configuration;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Map;
import java.util.Set;

@Configuration("configuration")
@Order(1)
public class ApplicationConfigurationInFile implements ApplicationConfiguration {
    private final Map<String, Object> configuration;
    private final Set<String> usingConfigNames;

    public ApplicationConfigurationInFile(ConfigurationFileMapperSpring fileMapper) {
        this.configuration = fileMapper.getConfigMap();
        this.usingConfigNames = fileMapper.getUsingConfigsNames();
    }

    @Override
    public Set<String> getUsingConfigsNames() {
        return this.usingConfigNames;
    }

    @Override
    public String get(String key) {
        return String.valueOf(this.configuration.get(key));
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt(String.valueOf(this.configuration.get(key)));
    }

    @Override
    public double getDouble(String key) {
        return Double.parseDouble(String.valueOf(this.configuration.get(key)));
    }

    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(String.valueOf(this.configuration.get(key)));
    }
}
