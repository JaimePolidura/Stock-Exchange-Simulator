package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ListedCompanyCurrencyCode extends StringValueObject {
    public ListedCompanyCurrencyCode(String value) {
        super(value);
    }

    public ListedCompanyCurrencyCode(){
        super(null);
    }

    public static ListedCompanyCurrencyCode of(String value){
        return new ListedCompanyCurrencyCode(value);
    }
}
