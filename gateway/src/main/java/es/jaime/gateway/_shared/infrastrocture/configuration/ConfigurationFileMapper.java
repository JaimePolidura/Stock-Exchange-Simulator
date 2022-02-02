package es.jaime.gateway._shared.infrastrocture.configuration;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class ConfigurationFileMapper {
    private final ConfigurationFileResolver configurationFileResolver;

    public ConfigurationFileMapper(ConfigurationFileResolver configurationFileResolver) {
        this.configurationFileResolver = configurationFileResolver;
    }

    public Map<String, Object> getConfigMap(){
        Map<String, Object> configMap = new HashMap<>();
        InputStream inputStream = configurationFileResolver.resolve();

        String[] lines = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next().split("\n");

        for (String line : lines) {
            String[] lineSplitByEqual = line.split("=");
            String key = lineSplitByEqual[0];
            Object value = lineSplitByEqual[1];

            configMap.put(key, value);
        }

        return configMap;
    }
}
