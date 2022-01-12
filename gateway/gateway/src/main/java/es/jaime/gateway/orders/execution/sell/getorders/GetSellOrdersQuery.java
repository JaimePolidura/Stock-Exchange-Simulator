package es.jaime.gateway.orders.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public final class GetSellOrdersQuery implements Query {
    @Getter private final List<OrderState> orderStates;
    @Getter private final OrderClientId clientId;

    public GetSellOrdersQuery(String clientId, List<String> orderStatesStrings) {
        this.clientId = OrderClientId.of(clientId);
        this.orderStates = orderStatesStrings.stream()
                .filter(OrderState::isValidState)
                .map(OrderState::of)
                .collect(Collectors.toList());
    }
}
