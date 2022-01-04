package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.Order;

public interface OrderCancellationProcessor {
    void cancel(Order order, CancelOrder cancelOrder);
}
