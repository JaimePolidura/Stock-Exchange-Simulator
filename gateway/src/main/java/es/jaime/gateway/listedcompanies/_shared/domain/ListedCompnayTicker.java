package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class ListedCompnayTicker extends StringValueObject {
    public ListedCompnayTicker(String value) {
        super(value);
    }

    public static ListedCompnayTicker of(String value){
        return new ListedCompnayTicker(value);
    }
}
