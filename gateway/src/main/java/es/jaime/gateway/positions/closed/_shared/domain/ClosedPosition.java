package es.jaime.gateway.positions.closed._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.positions._shared.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClosedPosition extends Aggregate {
    @Getter private PositionId positionId;
    @Getter private PositionClientId clientId;
    @Getter private PositionTicker ticker;
    @Getter private PositionQuantity quantity;
    @Getter private PositionOpeningPrice openingPrice;
    @Getter private PositionOpeningDate openingDate;
    @Getter private ClosedPositionClosingPrice closingPrice;
    @Getter private ClosedPositionClosingDate closingDate;
    @Getter private PositionStatus status = PositionStatus.closed();

    public ClosedPosition () {}

    public static ClosedPosition create(String clientId, String ticker, int quantity, double openingPrice,
                                        String openingDate, double closingPrice, String closingDate){

        return new ClosedPosition(PositionId.generate(), PositionClientId.from(clientId), PositionTicker.of(ticker),
                PositionQuantity.of(quantity), PositionOpeningPrice.of(openingPrice), PositionOpeningDate.of(openingDate),
                ClosedPositionClosingPrice.from(closingPrice), ClosedPositionClosingDate.from(closingDate), PositionStatus.closed());
    }
}