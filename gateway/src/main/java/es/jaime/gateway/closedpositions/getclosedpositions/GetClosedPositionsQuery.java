package es.jaime.gateway.closedpositions.getclosedpositions;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway.closedpositions._shared.domain.ClosedPositionClientId;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetClosedPositionsQuery implements Query {
    @Getter private ClosedPositionClientId clientId;

    public GetClosedPositionsQuery(String clientId) {
        this.clientId = ClosedPositionClientId.from(clientId);
    }
}
