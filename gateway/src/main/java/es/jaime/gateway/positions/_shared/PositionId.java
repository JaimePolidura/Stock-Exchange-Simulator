package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public class PositionId extends StringValueObject {
    public PositionId(String value) {
        super(value);
    }

    public static PositionId of (String value){
        return new PositionId(value);
    }

    public static PositionId generate(){
        return new PositionId(UUID.randomUUID().toString());
    }
}
