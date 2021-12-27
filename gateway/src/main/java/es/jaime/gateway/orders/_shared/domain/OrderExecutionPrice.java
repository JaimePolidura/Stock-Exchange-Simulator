package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public final class OrderExecutionPrice extends DoubleValueObject {
    public OrderExecutionPrice(double value) {
        super(value);
    }

    public OrderExecutionPrice(){
        super(0);
    }

    public boolean isTypeLimit(){
        return value() != -1;
    }

    public boolean isTypeMarket(){
        return value() == -1;
    }

    public static OrderExecutionPrice of (double price){
        return new OrderExecutionPrice(price);
    }

}
