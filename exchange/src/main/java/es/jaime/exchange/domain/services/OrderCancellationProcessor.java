package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.TradeOrder;

public interface OrderCancellationProcessor {
    void cancel(TradeOrder order, CancelOrder cancelOrder);
}
