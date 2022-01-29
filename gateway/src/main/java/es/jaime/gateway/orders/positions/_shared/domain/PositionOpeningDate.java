package es.jaime.gateway.orders.positions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;

public final class PositionOpeningDate extends StringValueObject {
    public PositionOpeningDate(String value) {
        super(value);
    }

    public PositionOpeningDate () {super(null);}

    public static PositionOpeningDate of(String value){
        return new PositionOpeningDate(value);
    }

    public static PositionOpeningDate of(LocalDateTime value){
        return new PositionOpeningDate(value.toString());
    }
}
