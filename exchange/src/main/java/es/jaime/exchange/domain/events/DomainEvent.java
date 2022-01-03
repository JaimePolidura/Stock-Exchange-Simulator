package es.jaime.exchange.domain.events;

import java.util.Map;

public abstract class DomainEvent {
    public abstract DomainEvent fromPrimitives(Map<String, Object> primitives);

    public String eventName(){
        return "";
    }
}
