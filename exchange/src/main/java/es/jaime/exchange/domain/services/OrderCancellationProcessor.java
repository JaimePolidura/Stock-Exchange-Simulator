package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;

public interface OrderCancellationProcessor {
    void cancel(ExecutionOrder order, CancelOrder cancelOrder);
}
