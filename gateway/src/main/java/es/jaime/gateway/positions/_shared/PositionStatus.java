package es.jaime.gateway.positions._shared;

import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public class PositionStatus extends StringValueObject {
    public PositionStatus(String value) {
        super(value);

        if(!value.equals("OPEN") && !value.equals("CLOSED")){
            throw new IllegalType("Illegal name");
        }
    }

    public PositionStatus() {
        super(null);
    }

    public static PositionStatus open(){
        return new PositionStatus("OPEN");
    }

    public static PositionStatus closed(){
        return new PositionStatus("CLOSED");
    }

    public static PositionStatus of(String value){
        return new PositionStatus(value);
    }
}