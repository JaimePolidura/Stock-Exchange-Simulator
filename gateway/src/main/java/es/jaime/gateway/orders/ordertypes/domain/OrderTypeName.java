package es.jaime.gateway.orders.ordertypes.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class OrderTypeName extends StringValueObject {
    public OrderTypeName(String value) {
        super(value);
    }

    public OrderTypeName () {
        super(null);
    }

    public static OrderTypeName buy(){
        return new OrderTypeName("BUY");
    }

    public static OrderTypeName sell(){
        return new OrderTypeName("SELL");
    }
}
