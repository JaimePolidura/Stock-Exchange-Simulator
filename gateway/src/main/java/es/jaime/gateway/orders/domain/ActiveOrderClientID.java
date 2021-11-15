package es.jaime.gateway.orders.domain;

import es.jaime.gateway._shared.domain.valueObject.UUIDValueObject;

import java.util.UUID;

public final class ActiveOrderClientID extends UUIDValueObject {
    public ActiveOrderClientID(UUID uuid) {
        super(uuid);
    }

    public static ActiveOrderClientID of(UUID uuid){
        return new ActiveOrderClientID(uuid);
    }
}
