package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderClientID;
import lombok.Getter;

public class GetActiveOrdersQuery implements Query {
    @Getter private final ActiveOrderClientID clientID;

    public GetActiveOrdersQuery(String clientIDString) {
        this.clientID = ActiveOrderClientID.of(clientIDString);
    }
}
