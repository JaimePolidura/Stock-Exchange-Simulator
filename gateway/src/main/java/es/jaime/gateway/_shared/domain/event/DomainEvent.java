package es.jaime.gateway._shared.domain.event;


import java.io.Serializable;
import java.util.Map;

public abstract class DomainEvent implements Serializable {
    public abstract DomainEvent fromPrimitives(Map<String, Object> primitives);

    public String eventName(){
        return "";
    }
}
