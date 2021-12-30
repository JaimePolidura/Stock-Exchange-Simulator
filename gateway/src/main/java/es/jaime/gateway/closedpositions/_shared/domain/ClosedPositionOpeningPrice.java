package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public final class ClosedPositionOpeningPrice extends DoubleValueObject {
    public ClosedPositionOpeningPrice(double value) {
        super(value);
    }
    public ClosedPositionOpeningPrice() {
        super(0);
    }

    public static ClosedPositionOpeningPrice from(double value){
        return new ClosedPositionOpeningPrice(value);
    }

}
