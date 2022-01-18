package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ExceptionOccurred extends DomainEvent {
    @Getter private DomainException exception;

    public ExceptionOccurred() {}

    //Not needed
    @Override
    public ExceptionOccurred fromPrimitives(Map<String, Object> primitives) {
        return null;
    }

    //Not needed
    @Override
    public String messageId() {
        return null;
    }
}
