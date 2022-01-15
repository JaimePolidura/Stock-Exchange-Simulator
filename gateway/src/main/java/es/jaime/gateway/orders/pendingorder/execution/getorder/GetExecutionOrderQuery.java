package es.jaime.gateway.orders.pendingorder.execution.getorder;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import lombok.Getter;

public class GetExecutionOrderQuery implements Query {
    @Getter private final OrderId orderId;
    @Getter private final OrderClientId clientId;

    public GetExecutionOrderQuery(String orderId, String clientId) {
        this.orderId = OrderId.of(orderId);
        this.clientId = OrderClientId.of(clientId);
    }
}
