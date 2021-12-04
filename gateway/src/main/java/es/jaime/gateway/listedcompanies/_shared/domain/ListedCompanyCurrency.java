package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ListedCompanyCurrency extends StringValueObject {
    public ListedCompanyCurrency(String value) {
        super(value);
    }

    public ListedCompanyCurrency (){
        super(null);
    }

    public static ListedCompanyCurrency of(String value){
        return new ListedCompanyCurrency(value);
    }
}
