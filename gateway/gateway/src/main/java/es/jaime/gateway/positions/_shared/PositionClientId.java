package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class PositionClientId extends StringValueObject {
    public PositionClientId(String value) {
        super(value);
    }

    public PositionClientId () {
        super(null);
    }

    public static PositionClientId from(String value){
        return new PositionClientId(value);
    }
}
