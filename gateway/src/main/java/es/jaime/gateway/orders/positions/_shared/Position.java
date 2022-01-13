package es.jaime.gateway.orders.positions._shared;

import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

public abstract class Position extends Order {
    @Getter private final ExecutedOrderType executedOrderType;
    @Getter private final PositionOpeningPrice openingPrice;
    @Getter private final PositionOpeningDate openingDate;
    @Getter private final OrderQuantity quantity;

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
