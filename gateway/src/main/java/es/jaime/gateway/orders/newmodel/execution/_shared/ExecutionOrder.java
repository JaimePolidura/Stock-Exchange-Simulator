package es.jaime.gateway.orders.newmodel.execution._shared;

import es.jaime.gateway.orders.newmodel._shared.domain.*;
import lombok.Getter;

public abstract class ExecutionOrder extends Order {
    @Getter protected OrderQuantity quantity;
    @Getter protected OrderExecutionPrice executionPrice;

    public ExecutionOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderType type, OrderState state,
                          OrderTicker ticker, OrderQuantity quantity, OrderExecutionPrice executionPrice) {
        super(orderId, clientId, date, type, state, ticker);
        this.quantity = quantity;
        this.executionPrice = executionPrice;
    }
}
