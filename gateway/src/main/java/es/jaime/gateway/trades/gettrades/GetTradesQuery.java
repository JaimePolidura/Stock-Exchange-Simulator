package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.trades._shared.domain.TradeClientId;
import lombok.Getter;

public final class GetTradesQuery implements Query {
    @Getter private final TradeClientId clientId;

    public GetTradesQuery(String clientId) {
        this.clientId = TradeClientId.of(clientId);
    }
}
