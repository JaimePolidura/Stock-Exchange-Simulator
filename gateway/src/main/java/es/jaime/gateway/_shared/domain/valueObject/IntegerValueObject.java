package es.jaime.gateway._shared.domain.valueObject;

public class IntegerValueObject extends ValueObject{
    private final int value;

    public IntegerValueObject(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
