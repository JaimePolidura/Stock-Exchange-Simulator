package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class OrderClientId extends StringValueObject {
    public OrderClientId(String clientId) {
        super(clientId);
    }

    public OrderClientId(){
        super(null);
    }

    public static OrderClientId of(String clientId){
        return new OrderClientId(clientId);
    }
}
