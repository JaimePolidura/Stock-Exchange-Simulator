package es.jaime.gateway._shared.domain;

public interface GatewayConfiguration {
    String get(String key);
    int getInt(String key);
    double getDouble(String key);
    boolean getBoolean(String key);
}
