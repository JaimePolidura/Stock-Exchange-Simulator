package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderTicker;

public final class ListedCompanyTicker extends StringValueObject {
    public ListedCompanyTicker(String value) {
        super(value);
    }

    public static ListedCompanyTicker of(String value){
        return new ListedCompanyTicker(value);
    }

    public static ListedCompanyTicker of(ActiveOrderTicker value){
        return new ListedCompanyTicker(value.toString());
    }
}
