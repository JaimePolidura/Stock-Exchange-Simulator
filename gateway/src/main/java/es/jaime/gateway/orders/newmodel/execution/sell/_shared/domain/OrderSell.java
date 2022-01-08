package es.jaime.gateway.orders.newmodel.execution.sell._shared.domain;

import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.newmodel.execution._shared.ExecutionOrder;
import lombok.Getter;

public class OrderSell extends ExecutionOrder {
    @Getter private OrderPositionIdToSell positionIdToSell;

    public OrderSell(OrderId orderId, OrderClientId clientId, OrderDate date, OrderType type, OrderState state, OrderTicker ticker,
                     OrderQuantity quantity, OrderExecutionPrice executionPrice, OrderPositionIdToSell positionIdToSell) {
        super(orderId, clientId, date, type, state, ticker, quantity, executionPrice);
        this.positionIdToSell = positionIdToSell;
    }

    public OrderSell () {
        super(null, null, null, null, null, null, null, null);
    }

    public static OrderSell create(String clientId, String ticker, int quantity, double executinPrice, String positionIdToSell){
        return new OrderSell(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice), OrderPositionIdToSell.of(positionIdToSell));
    }
}
