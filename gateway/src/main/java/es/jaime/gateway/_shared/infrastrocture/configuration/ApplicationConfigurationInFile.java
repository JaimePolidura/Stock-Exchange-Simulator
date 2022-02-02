package es.jaime.gateway._shared.infrastrocture.configuration;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration("configuration")
public class ApplicationConfigurationInFile implements ApplicationConfiguration {
    private final Map<String, Object> configuration;

    public ApplicationConfigurationInFile(ConfigurationFileMapper fileMapper) {
        this.configuration = fileMapper.getConfigMap();
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
