package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.configuration.DatabaseConfiguration;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Configuration("database-configuration")
public class DatabaseConnectionConfiguration extends DatabaseConfiguration {
    private final ResourcePatternResolver resourceResolver;

    public DatabaseConnectionConfiguration(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;

        super.runCommands();
    }

    @Override
    protected String url() {
        String link = "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&allowPublicKeyRetrieval=true";

        return String.format(link, "gateway-mysql", 3306, "sxs_gateway", "root", "");
    }

    @Override
    @SneakyThrows
    public List<String> getCommandsToRun() {
        Resource resource = this.resourceResolver.getResource("classpath:database/sxs_gateway.sql");
        String mysqlSentences = new Scanner(resource.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();

        return new ArrayList<>(Arrays.asList(mysqlSentences.split(";")));
    }
}
