package es.jaime.gateway.closedpositions._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class ClosedPositionId extends StringValueObject {
    public ClosedPositionId(String value) {
        super(value);
    }

    public ClosedPositionId () { super(null); }

    public static ClosedPositionId from(String value){
        return new ClosedPositionId(value);
    }

    public static ClosedPositionId generate(){
        return new ClosedPositionId(UUID.randomUUID().toString());
    }
}
