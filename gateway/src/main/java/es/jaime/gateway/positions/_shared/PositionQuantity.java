package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public class PositionQuantity extends IntegerValueObject {
    public PositionQuantity(int value) {
        super(value);
    }

    public static PositionQuantity of (int value){
        return new PositionQuantity(value);
    }
}
