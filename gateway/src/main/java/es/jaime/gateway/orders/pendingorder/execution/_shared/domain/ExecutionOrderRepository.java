package es.jaime.gateway.orders.pendingorder.execution._shared.domain;


import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;

import java.util.List;
import java.util.Optional;

public interface ExecutionOrderRepository<T extends ExecutionOrder> {
    void save(T order);

    Optional<T> findByOrderId(OrderId id);

    List<T> findByOrdersId(List<OrderId> ordersId);

    List<T> findByOrderClientIdAndState(OrderClientId clientId, OrderState state);

    void deleteByOrderId(OrderId orderId);

    List<T> findByTicker(OrderTicker orderTicker);
}
