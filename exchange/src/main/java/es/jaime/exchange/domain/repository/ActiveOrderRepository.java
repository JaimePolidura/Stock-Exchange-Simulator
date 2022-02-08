package es.jaime.exchange.domain.repository;

public interface ActiveOrderRepository {
    void save(String orderId);

    boolean existsByOrderId(String orderId);

    void removeByOrderId(String orderId);
}
