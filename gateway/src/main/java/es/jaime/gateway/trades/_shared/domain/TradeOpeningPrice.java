package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DoubleValueObject;

public final class TradeOpeningPrice extends DoubleValueObject {
    public TradeOpeningPrice(double value) {
        super(value);
    }

    public static TradeOpeningPrice of(double value){
        return new TradeOpeningPrice(value);
    }
}
