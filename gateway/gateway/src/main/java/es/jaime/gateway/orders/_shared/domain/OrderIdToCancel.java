package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

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
