package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.Arrays;

public class OrderState extends StringValueObject {
    public OrderState(String value) {
        super(value);

        this.ensureCorrectValue(value);
    }

    private void ensureCorrectValue(String value){
        if(!isValidState(value)){
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

    public static boolean isValidState(String stateString){
        return Arrays.stream(States.values())
                .anyMatch(state -> state.toString().equalsIgnoreCase(stateString));
    }

    private enum States {
        PENDING, CANCELED, ERROR, EXECUTED;
    }
}
