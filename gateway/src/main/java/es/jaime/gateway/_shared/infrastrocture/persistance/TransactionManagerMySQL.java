package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.connection.DatabaseConnection;
import es.jaime.gateway._shared.domain.database.TransactionManager;
import es.jaime.transacions.DatabaseTransacionManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionManagerMySQL implements TransactionManager {
    private final DatabaseTransacionManager databaseTransacionManager;

    public TransactionManagerMySQL(DatabaseConnection databaseConnection) {
        this.databaseTransacionManager = new DatabaseTransacionManager(databaseConnection);
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
