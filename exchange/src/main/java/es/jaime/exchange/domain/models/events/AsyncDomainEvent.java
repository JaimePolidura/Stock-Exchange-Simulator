package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.messages.MessageName;

import java.util.Map;

public interface AsyncDomainEvent{
    DomainEvent fromPrimitives(Map<String, Object> primitives);
    MessageName eventName();
}
