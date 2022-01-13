package es.jaime.gateway.orders.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import lombok.Getter;

public class GetClosedPositionsQuery implements Query {
    @Getter private final OrderClientId clientId;

    public GetClosedPositionsQuery(String clientId) {
        this.clientId = OrderClientId.of(clientId);
    }
}
