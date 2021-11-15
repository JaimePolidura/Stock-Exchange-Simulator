package es.jaime.gateway._shared.domain;

import java.util.Map;

public abstract class Aggregate {
    public abstract Map<String, Object> toPrimitives();
    public abstract Aggregate fromPrimitives(Map<String, Object> values);
}
