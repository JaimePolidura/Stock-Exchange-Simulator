package es.jaime.gateway.orders.positions._shared.domain;

public interface PositionRepository<T extends Position> {
    void save(T position);
}
