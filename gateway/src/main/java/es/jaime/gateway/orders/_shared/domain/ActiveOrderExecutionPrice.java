package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;
import lombok.val;

public final class ActiveOrderExecutionPrice extends DoubleValueObject {
    public ActiveOrderExecutionPrice(double value) {
        super(value);
    }


    public boolean isTypeLimit(){
        return getValue() != -1;
    }

    public boolean isTypeMarket(){
        return getValue() == -1;
    }

    public static ActiveOrderExecutionPrice of (double price){
        return new ActiveOrderExecutionPrice(price);
    }

}
