package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.connection.DatabaseConnection;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConnectionConfiguration extends DatabaseConnection{
    @Override
    protected String url() {
        String link = "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&allowPublicKeyRetrieval=true";

        return String.format(link, "gateway-mysql", 3306, "sxs_gateway", "root", "");
    }
}
