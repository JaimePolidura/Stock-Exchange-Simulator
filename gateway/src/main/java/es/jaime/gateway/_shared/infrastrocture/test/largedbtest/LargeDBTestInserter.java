package es.jaime.gateway._shared.infrastrocture.test.largedbtest;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.infrastrocture.configuration.AvailableConfigurations;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class LargeDBTestInserter implements CommandLineRunner {
    private final OpenPositionQueryInserter openPositionQueryInserter;
    private final ClosedPositionsQueryInserter closedPositionsQueryInserter;
    private final DatabaseConfiguration databaseConfiguration;
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        if(!correctConfig()) return;

        addOpenPositions();
    }

    private boolean correctConfig(){
        return this.configuration.getConfigName().equalsIgnoreCase(AvailableConfigurations.DEV_DB_LARGE_TEST);
    }

    @SneakyThrows
    private void addOpenPositions(){
        openPositionQueryInserter.insertAll();
        closedPositionsQueryInserter.insertAll();
    }
}
