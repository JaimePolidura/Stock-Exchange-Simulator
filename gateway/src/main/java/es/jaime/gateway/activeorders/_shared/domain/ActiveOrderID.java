package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import es.jaime.gateway._shared.domain.valueObject.UUIDValueObject;

import java.util.UUID;

public final class ActiveOrderID extends StringValueObject {
    public ActiveOrderID(String uuid) {
        super(uuid);
    }

    public ActiveOrderID(){
        super(null);
    }

    public String getOrderID(){
        return value();
    }

    public static ActiveOrderID generate(){
        return new ActiveOrderID(UUID.randomUUID().toString());
    }

    public static ActiveOrderID of(String value){
        return new ActiveOrderID(value);
    }
}
