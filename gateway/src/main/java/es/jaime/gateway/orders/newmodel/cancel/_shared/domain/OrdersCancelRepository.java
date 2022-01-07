package es.jaime.gateway.orders.newmodel.cancel._shared.domain;

import es.jaime.gateway.orders.newmodel._shared.OrderId;

import java.util.Optional;

public interface OrdersCancelRepository {
    void save(OrderCancel orderCancel);

    Optional<OrderCancel> findById(OrderId orderId);
}
