package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class ClosedPositionQuantity extends IntegerValueObject {
    public ClosedPositionQuantity(int value) {
        super(value);
    }

    public ClosedPositionQuantity() {
        super(1);
    }

    public static ClosedPositionQuantity from(int value){
        return new ClosedPositionQuantity(value);
    }
}
