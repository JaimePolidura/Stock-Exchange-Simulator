package es.jaime.gateway.orders.pendingorder.cancel._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrder;
import lombok.Getter;
import org.checkerframework.checker.units.qual.C;

public class CancelOrder extends PendingOrder {
    @Getter private OrderIdToCancel orderIdToCancel;

    public CancelOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker,
                       PendingOrderType pendingOrderType, OrderIdToCancel orderIdToCancel){
        super(orderId, clientId, date, pendingOrderType, state, ticker);
        this.orderIdToCancel = orderIdToCancel;
    }

    public CancelOrder() {
        super(null, null, null, null, null, null);
    }
    
    public static CancelOrder create(String clientId, String ticker, String orderIdToCancel){
        return new CancelOrder(
                OrderId.generate(),
                OrderClientId.of(clientId),
                OrderDate.now(),
                OrderState.pending(),
                OrderTicker.of(ticker),
                PendingOrderType.cancel(),
                OrderIdToCancel.of(orderIdToCancel)
        );
    }
}
