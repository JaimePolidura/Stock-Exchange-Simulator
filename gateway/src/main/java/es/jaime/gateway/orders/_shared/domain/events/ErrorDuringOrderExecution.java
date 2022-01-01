package es.jaime.gateway.orders._shared.domain.events;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorDuringOrderExecution extends DomainEvent {
    @Getter private final String orderId;
}
