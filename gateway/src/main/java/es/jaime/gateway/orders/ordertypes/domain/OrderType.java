package es.jaime.gateway.orders.ordertypes.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.Getter;

public final class OrderType extends Aggregate {
    @Getter private OrderTypeId orderTypeId;
    @Getter private OrderTypeName name;

    public OrderType(OrderTypeName name, OrderTypeId orderId) {
        this.name = name;
        this.orderTypeId = orderId;
    }

    public OrderType () {}
}
