package es.jaime.exchange.domain.models.events;

import java.util.Map;

public abstract class DomainEvent {
    public abstract DomainEvent fromPrimitives(Map<String, Object> primitives);

    public abstract String messageId();

    public String eventName(){
        return "";
    }
}
