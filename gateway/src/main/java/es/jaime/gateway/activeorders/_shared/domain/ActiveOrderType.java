package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.EnumValueObject;

public final class ActiveOrderType extends EnumValueObject<ActiveOrderTypeValues> {
    public ActiveOrderType(Enum<ActiveOrderTypeValues> value) {
        super(value);
    }

    public static ActiveOrderType of (ActiveOrderTypeValues value){
        return new ActiveOrderType(value);
    }

    public static ActiveOrderType of (String value){
        return new ActiveOrderType(ActiveOrderTypeValues.valueOf(value));
    }
}
