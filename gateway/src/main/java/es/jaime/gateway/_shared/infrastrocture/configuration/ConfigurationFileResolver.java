package es.jaime.gateway._shared.infrastrocture.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Configuration
public class ConfigurationFileResolver {
    private final ResourceLoader resourceLoader;

    public ConfigurationFileResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    public InputStream resolve(){
        return resourceLoader.getResource("classpath:config/configuration").getInputStream();
    }
}
