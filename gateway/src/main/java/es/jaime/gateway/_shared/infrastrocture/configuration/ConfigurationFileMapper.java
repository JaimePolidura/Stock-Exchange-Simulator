package es.jaime.gateway._shared.infrastrocture.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class ConfigurationFileMapper {
    private final ConfigurationFileResolver configurationFileResolver;

    public ConfigurationFileMapper(ConfigurationFileResolver configurationFileResolver) {
        this.configurationFileResolver = configurationFileResolver;
    }

    @SneakyThrows
    public Map<String, Object> getConfigMap(){
        Map<String, Object> configMap = new HashMap<>();
        InputStream inputStream = configurationFileResolver.resolve();

        Scanner scanner = new Scanner(inputStream);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            if(!isConfigLine(line)) continue;

            String[] lineSplitByEqual = line.split("=");
            String key = lineSplitByEqual[0];
            Object value = lineSplitByEqual.length == 1 ? "" : lineSplitByEqual[1];

            configMap.put(key, value);
        }

        return configMap;
    }

    private boolean isConfigLine(String line){
        return !line.startsWith("#") && (line.split("=").length == 2 || line.split("=").length == 1) ;
    }
}
