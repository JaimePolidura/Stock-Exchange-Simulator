package es.jaime.gateway._shared.domain.valueObject;

public class DoubleValueObject extends ValueObject{
    private final double value;

    public DoubleValueObject(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
