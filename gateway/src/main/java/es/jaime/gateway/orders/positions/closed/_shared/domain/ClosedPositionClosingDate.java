package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;

public final class ClosedPositionClosingDate extends StringValueObject {
    public ClosedPositionClosingDate(String value) {
        super(value);
    }

    public ClosedPositionClosingDate () {super(null);}

    public static ClosedPositionClosingDate of(String value){
        return new ClosedPositionClosingDate(value);
    }

    public static ClosedPositionClosingDate of(LocalDateTime value){
        return new ClosedPositionClosingDate(value.toString());
    }
}
