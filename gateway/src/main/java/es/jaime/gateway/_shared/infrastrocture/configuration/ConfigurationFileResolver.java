package es.jaime.gateway._shared.infrastrocture.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

import static java.lang.String.format;

@Configuration
public class ConfigurationFileResolver {
    private final ResourceLoader resourceLoader;

    public ConfigurationFileResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    public InputStream resolve(String file){
        return resourceLoader.getResource(format("classpath:%s", file)).getInputStream();
    }
}
