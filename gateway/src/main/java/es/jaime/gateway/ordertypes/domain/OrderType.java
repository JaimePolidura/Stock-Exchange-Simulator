package es.jaime.gateway.ordertypes.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.Getter;

public final class OrderType extends Aggregate {
    @Getter private OrderTypeName name;

    public OrderType(OrderTypeName name) {
        this.name = name;
    }

    public OrderType () {}
}
