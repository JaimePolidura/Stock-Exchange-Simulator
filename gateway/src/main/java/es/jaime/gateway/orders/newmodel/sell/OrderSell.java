package es.jaime.gateway.orders.newmodel.sell;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.orders.newmodel._shared.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OrderSell extends Aggregate {
    @Getter private OrderId orderId;
    @Getter private OrderClientId clientId;
    @Getter private OrderDate date;
    @Getter private OrderType type;
    @Getter private OrderState state;
    @Getter private OrderTicker ticker;
    @Getter private OrderQuantity quantity;
    @Getter private OrderExecutionPrice executionPrice;
    @Getter private OrderPositionIdToSell positionIdToSell;

    public OrderSell() {}

    public static OrderSell create(String clientId, String ticker, int quantity, double executinPrice, String positionIdToSell){
        return new OrderSell(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice), OrderPositionIdToSell.of(positionIdToSell));
    }
}
