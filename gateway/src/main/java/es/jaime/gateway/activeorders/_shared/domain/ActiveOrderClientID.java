package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import es.jaime.gateway._shared.domain.valueObject.UUIDValueObject;

import java.util.UUID;

public final class ActiveOrderClientID extends StringValueObject {
    public ActiveOrderClientID(String clientId) {
        super(clientId);
    }

    public ActiveOrderClientID (){
        super(null);
    }

    public static ActiveOrderClientID of(String clientId){
        return new ActiveOrderClientID(clientId);
    }
}
