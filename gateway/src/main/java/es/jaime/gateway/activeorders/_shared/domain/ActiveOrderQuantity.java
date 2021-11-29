package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class ActiveOrderQuantity extends IntegerValueObject {
    public ActiveOrderQuantity(int value) {
        super(value);
    }

    public ActiveOrderQuantity(){
        super(0);
    }

    public static ActiveOrderQuantity of(int value){
        return new ActiveOrderQuantity(value);
    }
}
