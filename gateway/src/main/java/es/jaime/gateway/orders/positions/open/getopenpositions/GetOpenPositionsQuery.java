package es.jaime.gateway.orders.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import lombok.Getter;

public final class GetOpenPositionsQuery implements Query {
    @Getter private final OrderClientId clientId;
    
    public GetOpenPositionsQuery(String clientId) {
        this.clientId = OrderClientId.of(clientId);
    }
}
