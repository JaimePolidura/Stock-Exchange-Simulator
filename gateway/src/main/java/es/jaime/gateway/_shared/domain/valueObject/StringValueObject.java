package es.jaime.gateway._shared.domain.valueObject;

public class StringValueObject extends ValueObject{
    private final String value;

    public StringValueObject(String value) {
        this.value = value;
    }

    public String value () {
        return this.value;
    }
}
