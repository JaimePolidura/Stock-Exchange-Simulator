package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class OrderTicker extends StringValueObject {
    public OrderTicker(String value) {
        super(value);
    }

    public OrderTicker(){
        super(null);
    }

    public static OrderTicker of(String ticker){
        return new OrderTicker(ticker);
    }
}
