package es.jaime.gateway.orders.pendingorder._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

public abstract class PendingOrder extends Order {
    @Getter protected final PendingOrderType pendingOrderType;

    public PendingOrder(OrderId orderId, OrderClientId clientId, OrderDate date, PendingOrderType type, OrderState state,
                        OrderTicker ticker) {
        super(orderId, clientId, date, state, ticker);
        this.pendingOrderType = type;
    }
}
