package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;
import lombok.Getter;

public class ClosedPosition extends Position {
    @Getter private ClosedPositionClosingPrice closingPrice;
    @Getter private ClosedPositionClosingDate closingDate;

    public ClosedPosition(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker, ExecutedOrderType executedOrderType,
                          PositionOpeningPrice openingPrice, ClosedPositionClosingPrice closingPrice, ClosedPositionClosingDate closingDate, OrderQuantity quantity,
                          PositionOpeningDate openingDate) {
        super(orderId, clientId, date, state, ticker, executedOrderType, openingPrice, quantity, openingDate);
        this.closingPrice = closingPrice;
        this.closingDate = closingDate;
    }

    public ClosedPosition () {
        super(null, null, null, null, null, null, null, null, null);
    }

    public static ClosedPosition create(String clientId, String ticker, int quantity, double openingPrice,
                                        String openingDate, double closingPrice, String closingDate){
        return new ClosedPosition(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), OrderState.executed(), OrderTicker.of(ticker), ExecutedOrderType.closed(),
                PositionOpeningPrice.of(openingPrice), ClosedPositionClosingPrice.from(closingPrice), ClosedPositionClosingDate.from(closingDate), OrderQuantity.of(quantity),
                PositionOpeningDate.from(openingDate));
    }
}
