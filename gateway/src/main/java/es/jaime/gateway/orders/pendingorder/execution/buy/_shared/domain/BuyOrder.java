package es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;

public final class BuyOrder extends ExecutionOrder {
    public BuyOrder(OrderId orderId, OrderClientId clientId, OrderDate date, PendingOrderType type, OrderState state,
                    OrderTicker ticker, OrderQuantity quantity, OrderPriceToExecute executionPrice) {
        super(orderId, clientId, date, type, state, ticker, quantity, executionPrice);
    }

    public BuyOrder() {
        super(null, null, null, null, null, null, null, null);
    }

    public BuyOrder changeStateTo(OrderState orderState){
        return new BuyOrder(orderId, clientId, date, pendingOrderType, orderState, ticker, quantity, priceToExecute);
    }

    public BuyOrder updateQuantity(OrderQuantity orderQuantity){
        return new BuyOrder(orderId, clientId, date, pendingOrderType, state, ticker, orderQuantity, priceToExecute);
    }

    public BuyOrder updateExecutionPrice(OrderPriceToExecute executionPrice){
        return new BuyOrder(orderId, clientId, date, pendingOrderType, state, ticker, quantity, executionPrice);
    }

    public static BuyOrder create(String clientId, String ticker, int quantity, double executinPrice){
        return new BuyOrder(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), PendingOrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderPriceToExecute.of(executinPrice));
    }
}
