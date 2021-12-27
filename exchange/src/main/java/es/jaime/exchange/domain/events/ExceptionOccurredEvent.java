package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ExceptionOccurredEvent extends DomainEvent {
    @Getter private final DomainException exception;
}
