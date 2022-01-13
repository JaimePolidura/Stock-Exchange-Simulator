package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;

public final class OpenPosition extends Position {
    public OpenPosition (OrderId orderId, OrderClientId clientId, OrderDate date, OrderTicker ticker, OrderQuantity quantity,
                         PositionOpeningPrice openingPrice, PositionOpeningDate openingDate, ExecutedOrderType executedOrderType,
                         OrderState state) {
        super(orderId, clientId, date, state, ticker, executedOrderType, openingPrice, quantity, openingDate);
    }

    public OpenPosition () {
        super(null, null, null, null, null, null, null, null, null);
    }

    public OpenPosition decreasyQuantityBy(int value){
        OrderQuantity newQuantity = OrderQuantity.of(quantity.value() - value);

        return new OpenPosition(orderId, clientId, date, ticker, newQuantity, openingPrice, openingDate, executedOrderType, state);
    }

    public static OpenPosition create(String clientId, String ticker, double openingPrice, int quantity){
        OrderDate dateNow = OrderDate.now();

        return new OpenPosition(OrderId.generate(), OrderClientId.of(clientId), dateNow, OrderTicker.of(ticker), OrderQuantity.of(quantity),
                PositionOpeningPrice.of(openingPrice), PositionOpeningDate.of(dateNow.value()), ExecutedOrderType.open(), OrderState.executed());
    }
}
