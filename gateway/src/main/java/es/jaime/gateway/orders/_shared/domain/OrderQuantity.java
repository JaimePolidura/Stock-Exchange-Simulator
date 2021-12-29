package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class OrderQuantity extends IntegerValueObject {
    public OrderQuantity(int value) {
        super(value);

        ensureCorrectValue(value);
    }

    private void ensureCorrectValue(int value){
        if(value <= 0){
            throw new IllegalQuantity("The quantity should be more than 0");
        }
    }

    public OrderQuantity(){
        super(1);
    }

    public static OrderQuantity of(int value){
        return new OrderQuantity(value);
    }
}
