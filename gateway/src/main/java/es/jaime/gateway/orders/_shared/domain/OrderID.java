package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class OrderID extends StringValueObject {
    public OrderID(String uuid) {
        super(uuid);
    }

    public OrderID(){
        super(null);
    }

    public String getOrderID(){
        return value();
    }

    public static OrderID generate(){
        return new OrderID(UUID.randomUUID().toString());
    }

    public static OrderID of(String value){
        return new OrderID(value);
    }
}
