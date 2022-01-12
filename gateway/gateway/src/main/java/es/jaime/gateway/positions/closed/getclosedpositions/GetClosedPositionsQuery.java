package es.jaime.gateway.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.positions._shared.PositionClientId;
import lombok.Getter;

public class GetClosedPositionsQuery implements Query {
    @Getter private final PositionClientId clientId;

    public GetClosedPositionsQuery(String clientId) {
        this.clientId = PositionClientId.from(clientId);
    }
}
