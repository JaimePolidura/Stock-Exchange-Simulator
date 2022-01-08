package es.jaime.gateway.orders.cancel._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;

import java.util.List;
import java.util.Optional;

public interface OrdersCancelRepository {
    void save(OrderCancel orderCancel);

    Optional<OrderCancel> findById(OrderId orderId);

    List<OrderCancel> findByOrderStateAndClientId(OrderState orderState, OrderClientId clientId);
}
