package es.jaime.gateway.orders.pendingorder.cancel._shared.domain;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class OrderCancelled extends DomainEvent {
    @Getter private String clientId;
    @Getter private String orderId;
    @Getter private String orderIdCancelled;

    public OrderCancelled () {}

    @Override
    public EventName eventName() {
        return EventName.ORDER_CANCELLED;
    }

    @Override
    public OrderCancelled fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new OrderCancelled(
                String.valueOf(body.get("clientId")),
                String.valueOf(body.get("orderId")),
                String.valueOf(body.get("orderIdCancelled"))
        );
    }
}
