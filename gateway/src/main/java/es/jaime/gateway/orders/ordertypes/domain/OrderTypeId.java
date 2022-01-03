package es.jaime.gateway.orders.ordertypes.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;
import es.jaime.gateway.orders.ordertypes.application.OrderTypes;

public final class OrderTypeId extends IntegerValueObject {
    public OrderTypeId(int value) {
        super(value);
    }

    public OrderTypeId() {
        super(-1);
    }

    public static OrderTypeId of (int value){
        return new OrderTypeId(value);
    }

    public static OrderTypeId buy(){
        return new OrderTypeId(OrderTypes.BUY);
    }

    public static OrderTypeId sell(){
        return new OrderTypeId(OrderTypes.SELL);
    }
}
