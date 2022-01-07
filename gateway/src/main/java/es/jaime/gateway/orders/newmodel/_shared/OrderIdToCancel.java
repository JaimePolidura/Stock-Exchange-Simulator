package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class OrderIdToCancel extends StringValueObject {
    public OrderIdToCancel(String uuid) {
        super(uuid);
    }

    public OrderIdToCancel(){
        super(null);
    }

    public static OrderIdToCancel of(String value){
        return new OrderIdToCancel(value);
    }
}
