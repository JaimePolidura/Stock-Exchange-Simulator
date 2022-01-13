package es.jaime.gateway.orders.positions._shared;

import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

public abstract class Position extends Order {
    @Getter protected final ExecutedOrderType executedOrderType;
    @Getter protected final PositionOpeningPrice openingPrice;
    @Getter protected final PositionOpeningDate openingDate;
    @Getter protected final OrderQuantity quantity;

    public Position(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker,
                    ExecutedOrderType executedOrderType, PositionOpeningPrice openingPrice, OrderQuantity quantity,
                    PositionOpeningDate openingDate) {
        super(orderId, clientId, date, state, ticker);
        this.executedOrderType = executedOrderType;
        this.openingPrice = openingPrice;
        this.openingDate = openingDate;
        this.quantity = quantity;
    }
}
