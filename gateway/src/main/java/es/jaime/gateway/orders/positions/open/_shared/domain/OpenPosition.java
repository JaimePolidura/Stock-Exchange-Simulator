package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;

public final class OpenPosition extends Position {
    public OpenPosition(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker,
                        ExecutedOrderType executedOrderType, PositionOpeningPrice openingPrice, OrderQuantity quantity,
                        PositionOpeningDate openingDate) {
        super(orderId, clientId, date, state, ticker, executedOrderType, openingPrice, quantity, openingDate);
    }

    public OpenPosition () {
        super(null, null, null, null, null, null, null, null, null);
    }

    public static OpenPosition create(String clientId, String ticker, double openingPrice, int quantity){
        OrderDate dateNow = OrderDate.now();

        return new OpenPosition(OrderId.generate(), OrderClientId.of(clientId), dateNow, OrderState.executed(), OrderTicker.of(ticker),
                ExecutedOrderType.closed(), PositionOpeningPrice.of(openingPrice), OrderQuantity.of(quantity), PositionOpeningDate.from(dateNow.value()));
    }
}
