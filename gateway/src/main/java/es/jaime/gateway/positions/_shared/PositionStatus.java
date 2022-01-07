package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.valueObject.EnumValueObject;

public class PositionStatus extends EnumValueObject<PositionStatus.PositionStatusValues> {
    public PositionStatus(PositionStatusValues value) {
        super(value);
    }

    public static PositionStatus of(PositionStatusValues value){
        return new PositionStatus(value);
    }

    public enum PositionStatusValues {
        OPEN, CLOSED
    }
}