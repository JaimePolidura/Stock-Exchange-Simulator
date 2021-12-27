package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.EnumValueObject;

public final class OrderStatus extends EnumValueObject<OrderStatus.Status> {
    public OrderStatus(Enum<Status> value) {
        super(value);
    }

    public OrderStatus(){
        super(Status.SENDING);
    }

    public static OrderStatus of(String value){
        return new OrderStatus(Status.valueOf(value));
    }

    public static OrderStatus of(Status value){
        return new OrderStatus(value);
    }

    public enum Status{
        SENDING, SEARCHING_PLAYER
    }
}
