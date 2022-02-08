package es.jaime.gateway.orders.pendingorder._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderTicker;

import java.util.List;
import java.util.Optional;

public interface PendingOrderRepository<T extends PendingOrder> {
    void save(T order);

    Optional<T> findByOrderId(OrderId orderId);

    List<T> findByTicker(OrderTicker ticker);

    List<T> findByOrdersId(List<OrderId> ordersId);
}
