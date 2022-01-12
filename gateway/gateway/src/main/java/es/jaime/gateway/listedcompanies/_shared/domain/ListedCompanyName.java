package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ListedCompanyName extends StringValueObject {
    public ListedCompanyName(String value) {
        super(value);
    }

    public ListedCompanyName() { super(null); }

    public static ListedCompanyName of (String value){
        return new ListedCompanyName(value);
    }
}
