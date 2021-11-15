package es.jaime.gateway.authentication._shared.domain;


import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class UserPassword extends StringValueObject {
    public UserPassword(String value) {
        super(value);
    }

    public static UserPassword of(String value){
        return new UserPassword(value);
    }
}
