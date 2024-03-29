package es.jaime.gateway.orders.positions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public class PositionOpeningPrice extends DoubleValueObject {
    public PositionOpeningPrice(double value) {
        super(value);
    }

    public PositionOpeningPrice () {
        super(0);
    }

    public static PositionOpeningPrice of(double value){
        return new PositionOpeningPrice(value);
    }
}
