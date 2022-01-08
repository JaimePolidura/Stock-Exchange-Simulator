package es.jaime.gateway.orders.execution._shared.domain;


import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;

import java.util.List;
import java.util.Optional;

public interface ExecutionOrderRepository<T extends ExecutionOrder> {
    void save(T order);

    Optional<T> findByOrderId(OrderId id);

    List<T> findByOrderClientIdAndState(OrderClientId clientId, OrderState state);
}
