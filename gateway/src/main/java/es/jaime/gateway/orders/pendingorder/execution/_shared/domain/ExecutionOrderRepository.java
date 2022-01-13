package es.jaime.gateway.orders.pendingorder.execution._shared.domain;


import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;

import java.util.List;
import java.util.Optional;

public interface ExecutionOrderRepository<T extends ExecutionOrder> {
    void save(T order);

    Optional<T> findByOrderId(OrderId id);

    List<T> findByOrderClientIdAndState(OrderClientId clientId, OrderState state);

    Optional<T> findLastOrderByStateAndByTicker(OrderState orderState, OrderTicker orderTicker);
}
