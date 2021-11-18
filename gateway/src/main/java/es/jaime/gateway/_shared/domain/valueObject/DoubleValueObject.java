package es.jaime.gateway._shared.domain.valueObject;

import java.util.Objects;

public class DoubleValueObject extends ValueObject{
    private final double value;

    public DoubleValueObject(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleValueObject that = (DoubleValueObject) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
