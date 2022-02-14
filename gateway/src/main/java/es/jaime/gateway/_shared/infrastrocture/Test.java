package es.jaime.gateway._shared.infrastrocture;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Test implements CommandLineRunner {
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        configuration.getUsingConfigsNames().forEach(System.out::println);

        System.out.println(configuration.get("MYSQL_DB_SEEDER"));
        System.out.println(configuration.get("HOLA"));
    }
}
