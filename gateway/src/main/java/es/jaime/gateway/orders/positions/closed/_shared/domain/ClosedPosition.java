package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;
import lombok.Getter;

public class ClosedPosition extends Position {
    @Getter private ClosedPositionClosingPrice closingPrice;
    @Getter private ClosedPositionClosingDate closingDate;

    public ClosedPosition(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker, OrderQuantity quantity,
                          ExecutedOrderType executedOrderType, PositionOpeningPrice openingPrice, PositionOpeningDate openingDate, ClosedPositionClosingPrice closingPrice,
                          ClosedPositionClosingDate closingDate){
        super(orderId, clientId, date, state, ticker, executedOrderType, openingPrice, quantity, openingDate);
        this.closingPrice = closingPrice;
        this.closingDate = closingDate;
    }

    public ClosedPosition () {
        super(null, null, null, null, null, null, null, null, null);
    }

    public static ClosedPosition create(String clientId, String ticker, int quantity, double openingPrice,
                                        String openingDate, double closingPrice, String closingDate){
        return new ClosedPosition(
                OrderId.generate(),
                OrderClientId.of(clientId),
                OrderDate.now(),
                OrderState.executed(),
                OrderTicker.of(ticker),
                OrderQuantity.of(quantity),
                ExecutedOrderType.closed(),
                PositionOpeningPrice.of(openingPrice),
                PositionOpeningDate.of(openingDate),
                ClosedPositionClosingPrice.of(closingPrice),
                ClosedPositionClosingDate.of(closingDate)
        );
    }
}
