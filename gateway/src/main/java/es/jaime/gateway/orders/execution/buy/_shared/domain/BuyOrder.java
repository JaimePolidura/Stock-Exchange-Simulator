package es.jaime.gateway.orders.execution.buy._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution._shared.domain.ExecutionOrder;

public final class BuyOrder extends ExecutionOrder {
    public BuyOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderType type, OrderState state,
                    OrderTicker ticker, OrderQuantity quantity, OrderExecutionPrice executionPrice) {
        super(orderId, clientId, date, type, state, ticker, quantity, executionPrice);
    }

    public BuyOrder() {
        super(null, null, null, null, null, null, null, null);
    }

    public BuyOrder changeStateTo(OrderState orderState){
        return new BuyOrder(orderId, clientId, date, type, orderState, ticker, quantity, executionPrice);
    }

    public BuyOrder updateQuantity(OrderQuantity orderQuantity){
        return new BuyOrder(orderId, clientId, date, type, state, ticker, orderQuantity, executionPrice);
    }

    public static BuyOrder create(String clientId, String ticker, int quantity, double executinPrice){
        return new BuyOrder(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice));
    }
}
