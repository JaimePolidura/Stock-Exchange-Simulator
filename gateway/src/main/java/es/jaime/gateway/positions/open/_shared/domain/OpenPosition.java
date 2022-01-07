package es.jaime.gateway.positions.open._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.positions._shared.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class OpenPosition extends Aggregate {
    @Getter private PositionId positionId;
    @Getter private PositionClientId clientId;
    @Getter private PositionTicker ticker;
    @Getter private PositionOpeningPrice openingPrice;
    @Getter private PositionOpeningDate openingDate;
    @Getter private PositionQuantity quantity;

    public OpenPosition() {}
}
