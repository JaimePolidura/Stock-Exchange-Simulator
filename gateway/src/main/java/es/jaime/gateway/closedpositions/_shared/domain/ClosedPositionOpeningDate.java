package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ClosedPositionOpeningDate extends StringValueObject {
    public ClosedPositionOpeningDate(String value) {
        super(value);
    }

    public ClosedPositionOpeningDate() { super(null); }

    public static ClosedPositionOpeningDate from(String date){
        return new ClosedPositionOpeningDate(date);
    }
}
