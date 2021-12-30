package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ClosedPositionTicker extends StringValueObject {
    public ClosedPositionTicker(String value) {
        super(value);
    }

    public ClosedPositionTicker() {
        super(null);
    }

    public static ClosedPositionTicker from(String value){
        return new ClosedPositionTicker(value);
    }
}
