package es.jaime.gateway.orders.execution.sell._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.execution._shared.ExecutionOrder;
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


    public OrderSell changeStateTo(OrderState orderState){
        return new OrderSell(orderId, clientId, date, type, orderState, ticker, quantity, executionPrice, positionIdToSell);
    }

    public OrderSell updateQuantity(OrderQuantity orderQuantity){
        return new OrderSell(orderId, clientId, date, type, state, ticker, orderQuantity, executionPrice, positionIdToSell);
    }

    public static OrderSell create(String clientId, String ticker, int quantity, double executinPrice, String positionIdToSell){
        return new OrderSell(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderExecutionPrice.of(executinPrice), OrderPositionIdToSell.of(positionIdToSell));
    }
}
