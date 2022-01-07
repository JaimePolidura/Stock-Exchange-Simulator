package es.jaime.gateway.positions.closed._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ClosedPositionClosingDate extends StringValueObject {
    public ClosedPositionClosingDate(String value) {
        super(value);
    }

    public ClosedPositionClosingDate () {super(null);}

    public static ClosedPositionClosingDate from(String value){
        return new ClosedPositionClosingDate(value);
    }
}
