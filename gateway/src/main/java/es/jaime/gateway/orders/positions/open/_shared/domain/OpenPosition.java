package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.Position;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;

import java.util.HashMap;
import java.util.Map;

public final class OpenPosition extends Position {
    public OpenPosition(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker, OrderQuantity quantity,
                        ExecutedOrderType executedOrderType, PositionOpeningPrice openingPrice, PositionOpeningDate openingDate){
        super(orderId, clientId, date, state,ticker, executedOrderType, openingPrice, quantity, openingDate);
    }

    public OpenPosition () {
        super(null, null, null, null, null, null, null, null, null);
    }

    public OpenPosition decreasyQuantityBy(int value){
        OrderQuantity newQuantity = OrderQuantity.of(quantity.value() - value);

        return new OpenPosition(orderId, clientId, date, state, ticker, newQuantity, executedOrderType, openingPrice, openingDate);
    }

    public static OpenPosition create(String clientId, String ticker, double openingPrice, int quantity){
        OrderDate dateNow = OrderDate.now();

        return new OpenPosition(
                OrderId.generate(),
                OrderClientId.of(clientId),
                dateNow,
                OrderState.executed(),
                OrderTicker.of(ticker),
                OrderQuantity.of(quantity),
                ExecutedOrderType.open(),
                PositionOpeningPrice.of(openingPrice),
                PositionOpeningDate.of(dateNow.value())
        );
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("orderId", getOrderId().value());
            put("clientId", getClientId().value());
            put("date", getDate().value());
            put("state", getState().value());
            put("ticker", getTicker().value());
            put("quantity", getQuantity().value());
            put("executedOrderType", getExecutedOrderType().value());
            put("openingPrice", getOpeningPrice().value());
            put("openingDate", getOpeningDate().value());
        }};
    }
}
