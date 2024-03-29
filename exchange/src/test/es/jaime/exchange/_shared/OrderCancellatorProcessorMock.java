package es.jaime.exchange._shared;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.services.OrderCancellationProcessor;

public class OrderCancellatorProcessorMock implements OrderCancellationProcessor {
    private ExecutionOrder lastOrderExecuted;

    @Override
    public void cancel(ExecutionOrder order, CancelOrder cancelOrder) {
        this.lastOrderExecuted = order;
    }

    public ExecutionOrder getLastOrderExecuted() {
        return lastOrderExecuted;
    }
}
