package es.jaime.gateway.authentication._shared.domain;


import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

public final class UserName extends StringValueObject {
    public UserName(String value) {
        super(value);
    }

    public static UserName of(String value) {
        return new UserName(value);
    }
}
