package es.jaime.gateway._shared.infrastrocture.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ConfigurationFileMapper {
    private final ConfigurationFileResolver fileResolver;

    public ConfigurationFileMapper(ConfigurationFileResolver configurationFileResolver) {
        this.fileResolver = configurationFileResolver;
    }

    @SneakyThrows
    public Map<String, Object> getConfigMap(){
        Map<String, Object> configMap = configMapFrom("configuration/config");

        if(usingOtherConfigs(configMap))
            configMap = configMapFrom((String) configMap.get("USING"));

        return configMap;
    }

    private boolean usingOtherConfigs(Map<String, Object> configMap){
        return configMap.containsKey("USING");
    }

    private Map<String, Object> configMapFrom(String file){
        Map<String, Object> configMap = new HashMap<>();
        Scanner scanner = new Scanner(fileResolver.resolve(file));

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            if(isNotConfigLine(line)) continue;

            String[] lineSplitByEqual = line.split("=");
            String key = lineSplitByEqual[0];
            Object value = lineSplitByEqual.length == 1 ? "" : lineSplitByEqual[1];

            configMap.put(key, value);
        }

        return configMap;
    }

    private boolean isNotConfigLine(String line){
        return line.startsWith("#") || (line.split("=").length != 2 && line.split("=").length != 1);
    }
}
