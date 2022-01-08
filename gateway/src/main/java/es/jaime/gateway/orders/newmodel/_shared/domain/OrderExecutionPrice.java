package es.jaime.gateway.orders.newmodel._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public class OrderExecutionPrice extends DoubleValueObject {
    public OrderExecutionPrice(double value) {
        super(value);
    }

    public OrderExecutionPrice () {
        super(0);
    }

    public static OrderExecutionPrice of(double value){
        return new OrderExecutionPrice(value);
    }
}
