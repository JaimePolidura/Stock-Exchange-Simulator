package es.jaime.gateway.orders.pendingorder.execution._shared.domain;


import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderRepository;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;

import java.util.List;
import java.util.Optional;

public interface ExecutionOrderRepository<T extends ExecutionOrder> extends PendingOrderRepository<T> {
    List<T> findByOrderClientIdAndState(OrderClientId clientId, OrderState state);

    void deleteByOrderId(OrderId orderId);
}
