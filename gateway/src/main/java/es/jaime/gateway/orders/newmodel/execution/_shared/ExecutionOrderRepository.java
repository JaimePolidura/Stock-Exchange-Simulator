package es.jaime.gateway.orders.newmodel.execution._shared;


import es.jaime.gateway.orders.newmodel._shared.OrderId;

import java.util.Optional;

public interface ExecutionOrderRepository<T extends ExecutionOrder> {
    void save(T order);

    Optional<T> findByOrderId(OrderId id);
}
