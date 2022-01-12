package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class PositionTicker extends StringValueObject {
    public PositionTicker(String value) {
        super(value);
    }

    public PositionTicker () {
        super(null);
    }

    public static PositionTicker of(String value){
        return new PositionTicker(value);
    }
}
