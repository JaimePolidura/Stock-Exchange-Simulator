package es.jaime.gateway.orders.ordertypes.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class OrderTypeId extends IntegerValueObject {
    public OrderTypeId(int value) {
        super(value);
    }

    public OrderTypeId() {
        super(-1);
    }

    public static OrderTypeId of (int value){
        return new OrderTypeId(value);
    }
}
