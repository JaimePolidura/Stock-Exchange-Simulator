package es.jaime.gateway.orders.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class PositionOpeningDate extends StringValueObject {
    public PositionOpeningDate(String value) {
        super(value);
    }

    public PositionOpeningDate () {super(null);}

    public static PositionOpeningDate from(String value){
        return new PositionOpeningDate(value);
    }
}
