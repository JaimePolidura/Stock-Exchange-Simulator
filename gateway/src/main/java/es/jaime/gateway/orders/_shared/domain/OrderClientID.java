package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class OrderClientID extends StringValueObject {
    public OrderClientID(String clientId) {
        super(clientId);
    }

    public OrderClientID(){
        super(null);
    }

    public static OrderClientID of(String clientId){
        return new OrderClientID(clientId);
    }
}
