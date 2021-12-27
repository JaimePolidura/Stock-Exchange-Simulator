package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class TradeTicker extends StringValueObject {
    public TradeTicker(String value) {
        super(value);
    }

    public TradeTicker(){
        super(null);
    }

    public static TradeTicker of(String value){
        return new TradeTicker(value);
    }
}
