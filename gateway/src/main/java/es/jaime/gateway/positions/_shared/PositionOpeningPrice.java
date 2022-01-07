package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public class PositionOpeningPrice extends DoubleValueObject {
    public PositionOpeningPrice(double value) {
        super(value);
    }

    public static PositionOpeningPrice of(double value){
        return new PositionOpeningPrice(value);
    }
}
