package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public class OrderPriceToExecute extends DoubleValueObject {
    public OrderPriceToExecute(double value) {
        super(value);
    }

    public OrderPriceToExecute() {
        super(0);
    }

    public static OrderPriceToExecute of(double value){
        return new OrderPriceToExecute(value);
    }
}
