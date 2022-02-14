package es.jaime.gateway._shared.infrastrocture.configuration;

public final class AvailableConfigurations {
    private AvailableConfigurations() {}

    public static final String DEFAULT = "DEFAULT";
    public static final String DEV = "config.dev";
    public static final String DEV_DB_LARGE_TEST = "config.dev-large-db-test";

    public static final String DEV_DOCKER_COMPOSE = "config.dev-docker-compose";
    public static final String DEV_KUBERNETES = "config.dev-kubernetes";
}
