package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

@Configuration("database-configuration")
public class DatabaseConnectionConfiguration extends DatabaseConfiguration {
    private final ResourcePatternResolver resourceResolver;
    private final ApplicationConfiguration configuration;

    public DatabaseConnectionConfiguration(ResourcePatternResolver resourceResolver, ApplicationConfiguration configuration) {
        this.resourceResolver = resourceResolver;
        this.configuration = configuration;

        super.runCommands();
    }

    @Override
    protected String url() {
        String link = "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&allowPublicKeyRetrieval=true";

        return format(link,
                configuration.get("MYSQL_HOST"),
                configuration.get("MYSQL_PORT"),
                configuration.get("MYSQL_DB"),
                configuration.get("MYSQL_USER"),
                configuration.get("MYSQL_PASSWORD")
        );
    }

    @Override
    @SneakyThrows
    public List<String> getCommandsToRun() {
        Resource resource = this.resourceResolver.getResource(format("classpath:%s", configuration.get("MYSQL_DB_SEEDER")));
        String mysqlSentences = new Scanner(resource.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();

        return new ArrayList<>(Arrays.asList(mysqlSentences.split(";")));
    }
}
