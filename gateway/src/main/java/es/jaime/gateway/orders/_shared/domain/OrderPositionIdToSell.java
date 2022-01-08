package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class OrderPositionIdToSell extends StringValueObject {
    public OrderPositionIdToSell(String uuid) {
        super(uuid);
    }

    public OrderPositionIdToSell(){
        super(null);
    }

    public static OrderPositionIdToSell of(String value){
        return new OrderPositionIdToSell(value);
    }
}
