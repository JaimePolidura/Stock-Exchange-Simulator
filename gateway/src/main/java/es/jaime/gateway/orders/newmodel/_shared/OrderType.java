package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class OrderType extends StringValueObject {
    public OrderType(String value) {
        super(value);

        ensureCorrectValue(value);
    }

    private void ensureCorrectValue(String value){
        if(!value.equals("BUY") && !value.equals("CANCEL") && !value.equals("SELL")){
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
}
