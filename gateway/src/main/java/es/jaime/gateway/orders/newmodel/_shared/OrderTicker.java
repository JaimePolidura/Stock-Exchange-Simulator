package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class OrderTicker extends StringValueObject {
    public OrderTicker(String value) {
        super(value);
    }

    public OrderTicker () {
        super(null);
    }

    public static OrderTicker of(String value){
        return new OrderTicker(value);
    }
}
