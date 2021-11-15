package es.jaime.gateway.orders.domain;

import es.jaime.gateway._shared.domain.Aggregate;

import java.util.Map;

public final class ActiveOrder extends Aggregate {
    @Override
    public Map<String, Object> toPrimitives() {
        return null;
    }

    @Override
    public Aggregate fromPrimitives(Map<String, Object> values) {
        return null;
    }
}
