package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public final class ActiveOrderExecutionPrice extends DoubleValueObject {
    public ActiveOrderExecutionPrice(double value) {
        super(value);
    }

    public ActiveOrderExecutionPrice(){
        super(0);
    }

    public boolean isTypeLimit(){
        return value() != -1;
    }

    public boolean isTypeMarket(){
        return value() == -1;
    }

    public static ActiveOrderExecutionPrice of (double price){
        return new ActiveOrderExecutionPrice(price);
    }

}
