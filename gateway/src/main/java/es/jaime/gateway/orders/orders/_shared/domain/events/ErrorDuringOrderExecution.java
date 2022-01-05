package es.jaime.gateway.orders.orders._shared.domain.events;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.Getter;

import java.util.Map;

public class ErrorDuringOrderExecution extends DomainEvent {
    @Getter private String orderId;

    public ErrorDuringOrderExecution(String orderId) {
        this.orderId = orderId;
    }

    public ErrorDuringOrderExecution() {}

    @Override
    public ErrorDuringOrderExecution fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new ErrorDuringOrderExecution(String.valueOf(body.get("orderId")));
    }

    @Override
    public EventName eventName() {
        return EventName.ERROR_ORDER;
    }
}
