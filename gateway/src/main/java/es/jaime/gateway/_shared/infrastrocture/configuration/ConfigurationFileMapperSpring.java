package es.jaime.gateway._shared.infrastrocture.configuration;

import es.jaime.gateway._shared.domain.FileResolver;
import es.jaime.gateway._shared.domain.configuration.ConfigurationFileMapper;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ConfigurationFileMapperSpring implements ConfigurationFileMapper {
    private final FileResolver fileResolver;
    private final Map<String, Object> configMap;
    private final Set<String> usingConfigsNames;

    public ConfigurationFileMapperSpring(FileResolver fileResolver) {
        this.fileResolver = fileResolver;
        this.configMap = new HashMap<>();
        this.usingConfigsNames = new HashSet<>();

        loadConfig("configuration/config");
    }

    @Override
    public Map<String, Object> getConfigMap() {
        return this.configMap;
    }

    @Override
    public Set<String> getUsingConfigsNames() {
        return this.usingConfigsNames;
    }

    private void loadConfig(String file){
        Scanner scanner = new Scanner(fileResolver.resolve(file));

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            if(isNotConfigLine(line)) continue;

            String[] lineSplitByEqual = line.split("=");
            String key = lineSplitByEqual[0];
            String value = lineSplitByEqual.length == 1 ? "" : lineSplitByEqual[1];

            if(usingOtherConfig(key)){
                usingConfigsNames.add(getConfigName(value));
                loadConfig(value);
            }else{
                configMap.put(key, value);
            }
        }
    }

    private boolean isNotConfigLine(String line){
        return line.startsWith("#") || (line.split("=").length != 2 && line.split("=").length != 1);
    }

    private boolean usingOtherConfig(String key){
        return key.equalsIgnoreCase("USING");
    }

    private String getConfigName(String usingConfigPath){
        String[] pathSplitBySlash = usingConfigPath.split("/");

        return pathSplitBySlash[pathSplitBySlash.length - 1];
    }
}
