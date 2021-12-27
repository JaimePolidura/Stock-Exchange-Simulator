package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;

public final class TradeOpeningDate extends StringValueObject {
    public TradeOpeningDate(String value) {
        super(value);
    }

    public static TradeOpeningDate of(String value){
        return new TradeOpeningDate(value);
    }

    public static TradeOpeningDate now(){
        return new TradeOpeningDate(LocalDateTime.now().toString());
    }
}
