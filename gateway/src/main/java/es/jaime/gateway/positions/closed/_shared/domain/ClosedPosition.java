package es.jaime.gateway.positions.closed._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.closedpositions._shared.domain.ClosedPositionId;
import es.jaime.gateway.positions._shared.*;
import es.jaime.gateway.positions._shared.PositionStatus.PositionStatusValues;
import lombok.Getter;

public final class ClosedPosition extends Aggregate {
    @Getter private PositionId positionId;
    @Getter private PositionClientId clientId;
    @Getter private PositionTicker ticker;
    @Getter private PositionQuantity quantity;
    @Getter private PositionOpeningPrice openingPrice;
    @Getter private PositionOpeningDate openingDate;
    @Getter private ClosedPositionClosingPrice closingPrice;
    @Getter private ClosedPositionClosingDate closingDate;
    @Getter private PositionStatus status;

    private ClosedPosition () {}

    public ClosedPosition(PositionId positionId, PositionClientId clientId, PositionTicker ticker, PositionQuantity quantity,
                          PositionOpeningPrice openingPrice, PositionOpeningDate openingDate, ClosedPositionClosingPrice closingPrice,
                          ClosedPositionClosingDate closingDate) {
        this.positionId = positionId;
        this.clientId = clientId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.openingPrice = openingPrice;
        this.openingDate = openingDate;
        this.closingPrice = closingPrice;
        this.closingDate = closingDate;
        this.status =  PositionStatus.of(PositionStatusValues.CLOSED);
    }

    public static ClosedPosition create(String clientId, String ticker, int quantity, double openingPrice,
                                        String openingDate, double closingPrice, String closingDate){

        return new ClosedPosition(PositionId.generate(), PositionClientId.from(clientId), PositionTicker.of(ticker),
                PositionQuantity.of(quantity), PositionOpeningPrice.of(openingPrice), PositionOpeningDate.of(openingDate),
                ClosedPositionClosingPrice.from(closingPrice), ClosedPositionClosingDate.from(closingDate));
    }
}
