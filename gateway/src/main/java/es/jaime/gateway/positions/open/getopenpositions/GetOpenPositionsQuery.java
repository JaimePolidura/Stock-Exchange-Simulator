package es.jaime.gateway.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.positions._shared.PositionClientId;
import lombok.Getter;

public final class GetOpenPositionsQuery implements Query {
    @Getter private final PositionClientId clientId;

    public GetOpenPositionsQuery(String clientId) {
        this.clientId = PositionClientId.from(clientId);
    }
}
