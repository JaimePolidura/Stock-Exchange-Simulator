package es.jaime.exchange._shared;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.TradeOrder;
import es.jaime.exchange.domain.services.OrderCancellationProcessor;

public class OrderCancellatorProcessorMock implements OrderCancellationProcessor {
    @Override
    public void cancel(TradeOrder order, CancelOrder cancelOrder) {
        //TODO XD
    }
}
