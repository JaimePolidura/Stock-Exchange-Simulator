package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Trade extends Aggregate {
    @Getter private TradeId positionId;
    @Getter private TradeClientId clientId;
    @Getter private TradeTicker ticker;
    @Getter private TradeOpeningPrice openingPrice;
    @Getter private TradeOpeningDate openingDate;
    @Getter private TradeQuantity quantity;

    public Trade () {}
}
