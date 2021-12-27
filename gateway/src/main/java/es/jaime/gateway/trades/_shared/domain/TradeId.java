package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.util.UUID;

public final class TradeId extends StringValueObject {
    public TradeId(String value) {
        super(value);
    }

    public TradeId(){
        super(null);
    }

    public static TradeId of (String value){
        return new TradeId(value);
    }

    public static TradeId generate(){
        return new TradeId(UUID.randomUUID().toString());
    }
}
