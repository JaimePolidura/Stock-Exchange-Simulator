package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.UUIDValueObject;

import java.util.UUID;

public final class ActiveOrderID extends UUIDValueObject {
    public ActiveOrderID(UUID uuid) {
        super(uuid);
    }

    public static ActiveOrderID of(UUID uuid){
        return new ActiveOrderID(uuid);
    }
}
