package es.jaime.gateway._shared.domain;

import java.util.Set;

public interface ApplicationConfiguration {
    Set<String> getUsingConfigsNames();

    String get(String key);
    int getInt(String key);
    double getDouble(String key);
    boolean getBoolean(String key);

    default boolean usingConfig(String configName){
        return this.getUsingConfigsNames().contains(configName);
    }
}
