package es.jaime.gateway._shared.domain.valueObject;

import java.util.Objects;

public class StringValueObject extends ValueObject{
    private final String value;

    public StringValueObject(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValueObject that = (StringValueObject) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
