package es.jaime.gateway._shared.domain.valueObject;

public class EnumValueObject<E extends Enum<E>> extends ValueObject{
    private final Enum<E> value;
    private final String valueString;

    public EnumValueObject(Enum<E> value){
        this.value = value;
        this.valueString = value.toString();
    }

    public Enum<E> value(){
        return value;
    }

    public String valueString(){
        return valueString;
    }
}
