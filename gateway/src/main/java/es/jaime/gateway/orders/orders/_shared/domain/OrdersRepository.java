package es.jaime.gateway.orders.orders._shared.domain;

import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {
    void save(Order order);

    Optional<Order> findByOrderId(OrderId id);

    List<Order> findOrdersByTypeAndClientId(OrderTypeId orderTypeId, OrderClientId clientId);

    List<Order> findByClientId(OrderClientId clientID);

    void deleteByOrderId(OrderId id);
}
