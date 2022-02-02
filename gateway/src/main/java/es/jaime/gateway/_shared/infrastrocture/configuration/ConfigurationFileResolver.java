package es.jaime.gateway._shared.infrastrocture.configuration;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service
public class ConfigurationFileResolver {
    private final ResourceLoader resourceLoader;

    public ConfigurationFileResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    public InputStream resolve(){
        Resource resource = resourceLoader.getResource("classpath:config/configuration");

        return resource.getInputStream();
    }
}
