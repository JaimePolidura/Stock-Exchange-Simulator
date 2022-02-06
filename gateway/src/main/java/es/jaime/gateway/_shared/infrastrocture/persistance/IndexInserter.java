package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.configuration.DatabaseConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IndexInserter implements CommandLineRunner {
    private final DatabaseConfiguration databaseConfiguration;

    @Override
    public void run(String... args) throws Exception {
        List<String> indexStatements = List.of(
                "CREATE INDEX clientIdIndex ON orders (clientId)",
                "CREATE INDEX dateIndex ON orders (date)",
                "CREATE INDEX tickerIndex ON orders (ticker)"
        );

        for (String indexStatement : indexStatements) {
            try{
                databaseConfiguration.sendStatement(indexStatement);
            }catch (Exception e) {
                System.out.println("Error executing index " + e.getMessage());
            }
        }
    }
}
