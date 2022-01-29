package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway._shared.domain.database.TransactionManager;
import es.jaime.transacions.DatabaseTransacionManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionManagerMySQL implements TransactionManager {
    private final DatabaseTransacionManager databaseTransacionManager;

    public TransactionManagerMySQL(DatabaseConfiguration databaseConfiguration) {
        this.databaseTransacionManager = new DatabaseTransacionManager(databaseConfiguration);
    }

    @Override
    public void start() {
        this.databaseTransacionManager.start();
    }

    @Override
    public void commit() {
        this.databaseTransacionManager.commit();
    }

    @Override
    public void rollback() {
        this.databaseTransacionManager.rollback();
    }
}
