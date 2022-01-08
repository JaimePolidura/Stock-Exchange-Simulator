package es.jaime.gateway.orders.cancel._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;

import java.util.List;
import java.util.Optional;

public interface OrdersCancelRepository {
    void save(CancelOrder orderCancel);

    Optional<CancelOrder> findById(OrderId orderId);

    List<CancelOrder> findByClientIdAndOrderState(OrderClientId clientId, OrderState orderState);
}
