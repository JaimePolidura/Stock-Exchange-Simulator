package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.EnumValueObject;

public final class ActiveOrderStatus extends EnumValueObject<ActiveOrderStatus.Status> {
    public ActiveOrderStatus(Enum<Status> value) {
        super(value);
    }

    public ActiveOrderStatus (){
        super(Status.SENDING);
    }

    public static ActiveOrderStatus of(String value){
        return new ActiveOrderStatus(Status.valueOf(value));
    }

    public static ActiveOrderStatus of(Status value){
        return new ActiveOrderStatus(value);
    }

    public enum Status{
        SENDING, SEARCHING_PLAYER
    }
}
