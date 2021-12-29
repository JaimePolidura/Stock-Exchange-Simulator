package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.IllegalType;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class OrderTicker extends StringValueObject {
    public OrderTicker(String value) {
        super(value);
    }

    private void ensureNotNull(String value){
        if(value == null || value.equals("") || value.length() > 10){
            throw new IllegalType("The ticker should have a valid format");
        }
    }

    public OrderTicker(){
        super(null);
    }

    public static OrderTicker of(String ticker){
        return new OrderTicker(ticker);
    }
}
