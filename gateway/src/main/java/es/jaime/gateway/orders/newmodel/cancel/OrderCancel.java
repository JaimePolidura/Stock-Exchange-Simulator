package es.jaime.gateway.orders.newmodel.cancel;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.orders.newmodel._shared.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OrderCancel extends Aggregate {
    @Getter private OrderId orderId;
    @Getter private OrderClientId clientId;
    @Getter private OrderDate date;
    @Getter private OrderType type;
    @Getter private OrderState state;
    @Getter private OrderTicker ticker;
    @Getter private OrderIdToCancel orderIdToCancel;

    public OrderCancel() {}

    public static OrderCancel create(String clientId, String ticker, String orderIdToCancel){
        return new OrderCancel(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.cancel(), OrderState.pending(),
                OrderTicker.of(ticker), OrderIdToCancel.of(orderIdToCancel));
    }
}
