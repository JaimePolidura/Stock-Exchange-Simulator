package es.jaime.gateway._shared.infrastrocture;

import es.jaime.gateway._shared.domain.FileResolver;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static java.lang.String.format;

@Service
public class FileResolverSpring implements FileResolver {
    private final ResourceLoader resourceLoader;

    public FileResolverSpring(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    public InputStream resolve(String file){
        return resourceLoader.getResource(format("classpath:%s", file)).getInputStream();
    }
}
