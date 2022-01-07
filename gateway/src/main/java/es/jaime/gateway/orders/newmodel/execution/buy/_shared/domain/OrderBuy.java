package es.jaime.gateway.orders.newmodel.execution.buy._shared.domain;

import es.jaime.gateway.orders.newmodel._shared.*;
import es.jaime.gateway.orders.newmodel.execution._shared.ExecutionOrder;

public final class OrderBuy extends ExecutionOrder {

    public OrderBuy(OrderId orderId, OrderClientId clientId, OrderDate date, OrderType type, OrderState state,
                    OrderTicker ticker, OrderQuantity quantity, OrderExecutionPrice executionPrice) {
        super(orderId, clientId, date, type, state, ticker, quantity, executionPrice);
    }

    public OrderBuy () {
        super(null, null, null, null, null, null, null, null);
    }

    public static OrderBuy create(String clientId, String ticker, int quantity, double executinPrice){
        return new OrderBuy(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice));
    }
}
