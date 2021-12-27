package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class OrderQuantity extends IntegerValueObject {
    public OrderQuantity(int value) {
        super(value);
    }

    public OrderQuantity(){
        super(0);
    }

    public static OrderQuantity of(int value){
        return new OrderQuantity(value);
    }
}
