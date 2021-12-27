package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import es.jaime.gateway.orders._shared.domain.OrderTicker;

public final class ListedCompanyTicker extends StringValueObject {
    public ListedCompanyTicker(String value) {
        super(value);
    }

    public ListedCompanyTicker(){
        super(null);
    }

    public static ListedCompanyTicker of(String value){
        return new ListedCompanyTicker(value);
    }

    public static ListedCompanyTicker of(OrderTicker value){
        return new ListedCompanyTicker(value.toString());
    }
}
