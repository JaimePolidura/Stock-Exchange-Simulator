package es.jaime.gateway.orders.cancel._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

public class CancelOrder extends Order {
    @Getter private OrderIdToCancel orderIdToCancel;

    public CancelOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderType type, OrderState state,
                       OrderTicker ticker, OrderIdToCancel orderIdToCancel) {
        super(orderId, clientId, date, type, state, ticker);
        this.orderIdToCancel = orderIdToCancel;
    }

    public CancelOrder() {
        super(null, null, null, null, null, null);
    }

    public static CancelOrder create(String clientId, String ticker, String orderIdToCancel){
        return new CancelOrder(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.cancel(), OrderState.pending(),
                OrderTicker.of(ticker), OrderIdToCancel.of(orderIdToCancel));
    }
}
