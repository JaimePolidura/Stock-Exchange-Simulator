package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;

public class PositionOpeningDate extends StringValueObject {
    public PositionOpeningDate(String value) {
        super(value);
    }

    public static PositionOpeningDate of(String date){
        return new PositionOpeningDate(date);
    }

    public static PositionOpeningDate now(){
        return new PositionOpeningDate(LocalDateTime.now().toString());
    }
}
