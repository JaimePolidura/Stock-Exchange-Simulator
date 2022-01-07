package es.jaime.gateway.orders.newmodel.buy;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.orders.newmodel._shared.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.security.DenyAll;

@AllArgsConstructor
public class OrderBuy extends Aggregate {
    @Getter private OrderId orderId;
    @Getter private OrderClientId clientId;
    @Getter private OrderDate date;
    @Getter private OrderType type;
    @Getter private OrderState state;
    @Getter private OrderTicker ticker;
    @Getter private OrderQuantity quantity;
    @Getter private OrderExecutionPrice executionPrice;

    public OrderBuy() {}

    public static OrderBuy create(String clientId, String ticker, int quantity, double executinPrice){
        return new OrderBuy(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice));
    }
}
