package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.IntegerValueObject;

public final class TradeQuantity extends IntegerValueObject {
    public TradeQuantity(int value) {
        super(value);
    }

    public TradeQuantity(){
        super(0);
    }

    public static TradeQuantity of (int value){
        return new TradeQuantity(value);
    }
}
