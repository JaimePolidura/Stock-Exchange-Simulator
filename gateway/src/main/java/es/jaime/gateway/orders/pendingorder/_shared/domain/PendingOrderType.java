package es.jaime.gateway.orders.pendingorder._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.Arrays;

public class PendingOrderType extends StringValueObject {
    public PendingOrderType(String value) {
        super(value);

        ensureCorrectValue(value);
    }

    private void ensureCorrectValue(String value){
        if(!isValidType(value)){
            throw new IllegalType("Illegal value");
        }
    }

    public PendingOrderType() { super(null); }

    public boolean isBuy(){
        return this.value().equalsIgnoreCase(Types.BUY.toString());
    }

    public static PendingOrderType of(String value){
        return new PendingOrderType(value);
    }

    public static PendingOrderType buy(){
        return new PendingOrderType("BUY");
    }

    public static PendingOrderType sell(){
        return new PendingOrderType("SELL");
    }

    public static PendingOrderType cancel(){
        return new PendingOrderType("CANCEL");
    }

    public static boolean isValidType(String typeString){
        return Arrays.stream(Types.values())
                .anyMatch(type -> type.toString().equalsIgnoreCase(typeString));
    }

    private enum Types {
        BUY, SELL, CANCEL;
    }
}
