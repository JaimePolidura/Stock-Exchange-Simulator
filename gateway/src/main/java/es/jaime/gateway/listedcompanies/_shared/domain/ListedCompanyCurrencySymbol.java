package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ListedCompanyCurrencySymbol extends StringValueObject {
    public ListedCompanyCurrencySymbol(String value) {
        super(value);
    }

    public ListedCompanyCurrencySymbol(){
        super(null);
    }

    public static ListedCompanyCurrencySymbol of(String value){
        return new ListedCompanyCurrencySymbol(value);
    }
}
