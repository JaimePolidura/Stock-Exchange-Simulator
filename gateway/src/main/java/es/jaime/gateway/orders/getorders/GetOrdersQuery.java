package es.jaime.gateway.orders.getorders;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientID;
import lombok.Getter;

public class GetOrdersQuery implements Query {
    @Getter private final OrderClientID clientID;

    public GetOrdersQuery(String clientIDString) {
        this.clientID = OrderClientID.of(clientIDString);
    }
}
