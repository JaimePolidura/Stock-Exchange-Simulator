package es.jaime.gateway._shared.domain.database;

public interface TransactionManager {
    void start();

    void commit();

    void rollback();
}
