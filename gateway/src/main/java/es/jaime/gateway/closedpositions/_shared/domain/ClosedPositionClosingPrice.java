package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public final class ClosedPositionClosingPrice extends DoubleValueObject {
    public ClosedPositionClosingPrice(double value) {
        super(value);
    }

    public ClosedPositionClosingPrice () {
        super(0);
    }

    public static ClosedPositionClosingPrice from(double value){
        return new ClosedPositionClosingPrice(value);
    }
}
