package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ExceptionOccurred extends DomainEvent {
    @Getter private DomainException exception;
}
