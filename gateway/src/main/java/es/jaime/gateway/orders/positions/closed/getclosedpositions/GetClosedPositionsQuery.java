package es.jaime.gateway.orders.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.Query;
import lombok.Getter;

public class GetClosedPositionsQuery implements Query {
    @Getter private final PositionClientId clientId;

    public GetClosedPositionsQuery(String clientId) {
        this.clientId = PositionClientId.from(clientId);
    }
}
