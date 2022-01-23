package es.jaime.exchange.domain.models.events;

import java.util.Map;

public abstract class AsyncDomainEvent extends DomainEvent{
    public abstract DomainEvent fromPrimitives(Map<String, Object> primitives);
}
