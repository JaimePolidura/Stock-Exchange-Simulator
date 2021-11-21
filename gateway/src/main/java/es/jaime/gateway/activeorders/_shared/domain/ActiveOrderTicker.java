package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ActiveOrderTicker extends StringValueObject {
    public ActiveOrderTicker(String value) {
        super(value);
    }

    public static ActiveOrderTicker of(String ticker){
        return new ActiveOrderTicker(ticker);
    }
}
