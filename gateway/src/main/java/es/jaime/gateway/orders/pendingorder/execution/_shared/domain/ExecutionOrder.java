package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrder;
import lombok.Getter;

import java.util.Map;

public abstract class ExecutionOrder extends PendingOrder {
    @Getter protected OrderQuantity quantity;
    @Getter protected OrderPriceToExecute priceToExecute;

    public ExecutionOrder(OrderId orderId, OrderClientId clientId, OrderDate date, PendingOrderType pendingOrderType, OrderState state,
                          OrderTicker ticker, OrderQuantity quantity, OrderPriceToExecute executionPrice) {
        super(orderId, clientId, date, pendingOrderType, state, ticker);
        this.quantity = quantity;
        this.priceToExecute = executionPrice;
    }

    public boolean isBuy(){
        return super.pendingOrderType.isBuy();
    }
}
