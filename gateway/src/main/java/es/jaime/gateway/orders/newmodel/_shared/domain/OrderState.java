package es.jaime.gateway.orders.newmodel._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class OrderState extends StringValueObject {
    public OrderState(String value) {
        super(value);

        this.ensureCorrectValue(value);
    }

    private void ensureCorrectValue(String value){
        if(!value.equals("PENDING") && !value.equals("EXECUTED") && !value.equals("CANCELLED") && !value.equals("ERROR")) {
            throw new IllegalType("Illegal value");
        }
    }

    public OrderState () {
        super(null);
    }

    public static OrderState of(String value){
        return new OrderState(value);
    }

    public static OrderState cancelled(){
        return new OrderState("CANCELLED");
    }

    public static OrderState executed(){
        return new OrderState("EXECUTED");
    }

    public static OrderState error(){
        return new OrderState("ERROR");
    }

    public static OrderState pending(){
        return new OrderState("PENDING");
    }
}
