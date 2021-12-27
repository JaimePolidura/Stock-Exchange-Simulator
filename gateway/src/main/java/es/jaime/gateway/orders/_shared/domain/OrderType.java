package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.EnumValueObject;

public final class OrderType extends EnumValueObject<OrderTypeValues> {
    public OrderType(Enum<OrderTypeValues> value) {
        super(value);
    }

    public OrderType(){
        super(OrderTypeValues.BUY);
    }

    public static OrderType of (OrderTypeValues value){
        return new OrderType(value);
    }

    public static OrderType of (String value){
        return new OrderType(OrderTypeValues.valueOf(value));
    }
}
