package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class TradeClientId extends StringValueObject {
    public TradeClientId(String value) {
        super(value);
    }

    public static TradeClientId of(String value){
        return new TradeClientId(value);
    }
}
