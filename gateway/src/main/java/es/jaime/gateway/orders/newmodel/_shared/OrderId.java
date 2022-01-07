package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class OrderId extends StringValueObject {
    public OrderId(String uuid) {
        super(uuid);
    }

    public OrderId(){
        super(null);
    }

    public String getOrderID(){
        return value();
    }

    public static OrderId generate(){
        return new OrderId(UUID.randomUUID().toString());
    }

    public static OrderId of(String value){
        return new OrderId(value);
    }
}
