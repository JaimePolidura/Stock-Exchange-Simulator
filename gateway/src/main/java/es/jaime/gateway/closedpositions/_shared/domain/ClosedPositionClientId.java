package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class ClosedPositionClientId extends StringValueObject {
    public ClosedPositionClientId(String value) {
        super(value);
    }

    public ClosedPositionClientId() { super(null); }

    public static ClosedPositionClientId from(String value){
        return new ClosedPositionClientId(value);
    }

    public static ClosedPositionClientId generate(){
        return new ClosedPositionClientId(UUID.randomUUID().toString());
    }
}
