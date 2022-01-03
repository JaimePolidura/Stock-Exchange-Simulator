package es.jaime.gateway.orders.orders.getorderstype;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public final class GetOrdersByTypeQuery implements Query {
    @Getter private final List<OrderTypeId> types;
    @Getter private final OrderClientId clientId;

    public GetOrdersByTypeQuery(String clientId, List<Integer> types) {
        this.clientId = OrderClientId.of(clientId);
        this.types = types.stream()
                .map(OrderTypeId::of)
                .collect(Collectors.toList());
    }
}
