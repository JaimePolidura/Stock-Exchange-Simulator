package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class ExceptionOccurredEvent extends DomainEvent {
    @Getter private final DomainException exception;

    //Not needed
    @Override
    public ExceptionOccurredEvent fromPrimitives(Map<String, Object> primitives) {
        return null;
    }
}
