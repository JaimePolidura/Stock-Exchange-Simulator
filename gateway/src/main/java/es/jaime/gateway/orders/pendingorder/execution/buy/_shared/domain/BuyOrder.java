package es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;

public final class BuyOrder extends ExecutionOrder {
    public BuyOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker,
                    PendingOrderType pendingOrderType, OrderQuantity quantity, OrderPriceToExecute priceToExecute){
        super(orderId, clientId, date, pendingOrderType, state, ticker, quantity, priceToExecute);
    }

    public BuyOrder() {
        super(null, null, null, null, null, null, null, null);
    }

    public BuyOrder changeStateTo(OrderState orderState){
        return new BuyOrder(orderId, clientId, date, orderState, ticker, pendingOrderType, quantity, priceToExecute);
    }

    public BuyOrder updateQuantity(OrderQuantity orderQuantity){
        return new BuyOrder(orderId, clientId, date, state, ticker, pendingOrderType, orderQuantity, priceToExecute);
    }

    public BuyOrder updateExecutionPrice(OrderPriceToExecute executionPrice){
        return new BuyOrder(orderId, clientId, date, state, ticker, pendingOrderType, quantity, executionPrice);
    }

    public static BuyOrder create(String clientId, String ticker, int quantity, double executinPrice){
        return new BuyOrder(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderState.pending(),
                OrderTicker.of(ticker), PendingOrderType.buy(), OrderQuantity.of(quantity), OrderPriceToExecute.of(executinPrice));
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "orderId", orderId.value(),
                "clientId", clientId.value(),
                "date", date.value(),
                "state", state.value(),
                "ticker", ticker.value(),
                "pendingOrderType", pendingOrderType.value(),
                "quantity", quantity.value(),
                "executionPrice", priceToExecute.value()
        );
    }
}
