package es.jaime.gateway._shared.domain.event;


import es.jaime.gateway._shared.domain.EventName;

import java.io.Serializable;
import java.util.Map;

public abstract class DomainEvent implements Serializable {
    public abstract DomainEvent fromPrimitives(Map<String, Object> primitives);

    public EventName eventName(){
        return null;
    }
}
