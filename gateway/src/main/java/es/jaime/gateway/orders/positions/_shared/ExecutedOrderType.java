package es.jaime.gateway.orders.positions._shared;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.Arrays;

public class ExecutedOrderType extends StringValueObject {
    public ExecutedOrderType(String value) {
        super(value);

        ensureValidExecutedOrderType(value);
    }

    public static ExecutedOrderType of(String value){
        return new ExecutedOrderType(value);
    }

    private void ensureValidExecutedOrderType(String value){
        Arrays.stream(ExecutedOrderTypes.values())
                .map(Enum::toString)
                .filter(e -> e.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Invalid executed type"));
    }

    public static ExecutedOrderType closed(){
        return new ExecutedOrderType("CLOSED");
    }

    public static ExecutedOrderType open(){
        return new ExecutedOrderType("OPEN");
    }

    private enum ExecutedOrderTypes {
        CLOSED, OPEN
    }
}
