package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public class ClosedPositionClosingPrice extends DoubleValueObject {
    public ClosedPositionClosingPrice(double value) {
        super(value);
    }

    public ClosedPositionClosingPrice () {
        super(0);
    }

    public static ClosedPositionClosingPrice of(double value){
        return new ClosedPositionClosingPrice(value);
    }
}
