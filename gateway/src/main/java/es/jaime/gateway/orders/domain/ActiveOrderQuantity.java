package es.jaime.gateway.orders.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class ActiveOrderQuantity extends IntegerValueObject {
    public ActiveOrderQuantity(int value) {
        super(value);
    }

    public static ActiveOrderQuantity of(int value){
        return new ActiveOrderQuantity(value);
    }
}
