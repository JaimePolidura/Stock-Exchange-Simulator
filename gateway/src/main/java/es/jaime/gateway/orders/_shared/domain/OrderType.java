package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.Arrays;

public class OrderType extends StringValueObject {
    public OrderType(String value) {
        super(value);

        ensureCorrectValue(value);
    }

    private void ensureCorrectValue(String value){
        if(!isValidType(value)){
            throw new IllegalType("Illegal value");
        }
    }

    public OrderType () { super(null); }

    public static OrderType of(String value){
        return new OrderType(value);
    }

    public static OrderType buy(){
        return new OrderType("BUY");
    }

    public static OrderType sell(){
        return new OrderType("SELL");
    }

    public static OrderType cancel(){
        return new OrderType("CANCEL");
    }

    public static boolean isValidType(String typeString){
        return Arrays.stream(Types.values())
                .allMatch(type -> type.toString().equalsIgnoreCase(typeString));
    }

    private enum Types {
        BUY, SELL, CANCEL;
    }
}
