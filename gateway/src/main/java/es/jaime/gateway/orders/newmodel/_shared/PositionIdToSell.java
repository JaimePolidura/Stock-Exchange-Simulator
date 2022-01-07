package es.jaime.gateway.orders.newmodel._shared;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class PositionIdToSell extends StringValueObject {
    public PositionIdToSell(String uuid) {
        super(uuid);
    }

    public PositionIdToSell(){
        super(null);
    }

    public static PositionIdToSell of(String value){
        return new PositionIdToSell(value);
    }
}
