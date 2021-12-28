package es.jaime.gateway.orders._shared.domain;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {
    void save(Order order);

    Optional<Order> findByOrderId(OrderID id);

    Optional<List<Order>> findByClientId(OrderClientID clientID);

    void deleteByOrderId(OrderID id);
}
