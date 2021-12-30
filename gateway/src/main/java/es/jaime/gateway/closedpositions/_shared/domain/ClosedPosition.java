package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.Getter;

public final class ClosedPosition extends Aggregate {
    @Getter private ClosedPositionId closedPositionId;
    @Getter private ClosedPositionClientId clientId;
    @Getter private ClosedPositionTicker ticker;
    @Getter private ClosedPositionQuantity quantity;
    @Getter private ClosedPositionOpeningPrice openingPrice;
    @Getter private ClosedPositionOpeningDate openingDate;
    @Getter private ClosedPositionClosingPrice closingPrice;
    @Getter private ClosedPositionClosingDate closingDate;

    private ClosedPosition () {}

    public ClosedPosition(ClosedPositionId id, ClosedPositionClientId clientId, ClosedPositionTicker ticker, ClosedPositionQuantity quantity,
                          ClosedPositionOpeningPrice openingPrice, ClosedPositionOpeningDate openingDate, ClosedPositionClosingPrice closingPrice,
                          ClosedPositionClosingDate closingDate) {
        this.closedPositionId = id;
        this.clientId = clientId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.openingPrice = openingPrice;
        this.openingDate = openingDate;
        this.closingPrice = closingPrice;
        this.closingDate = closingDate;
    }

    public static ClosedPosition create(String clientId, String ticker, int quantity, double openingPrice,
                                        String openingDate, double closingPrice, String closingDate){

        return new ClosedPosition(ClosedPositionId.generate(), ClosedPositionClientId.from(clientId), ClosedPositionTicker.from(ticker),
                ClosedPositionQuantity.from(quantity), ClosedPositionOpeningPrice.from(openingPrice), ClosedPositionOpeningDate.from(openingDate),
                ClosedPositionClosingPrice.from(closingPrice), ClosedPositionClosingDate.from(closingDate));
    }
}
