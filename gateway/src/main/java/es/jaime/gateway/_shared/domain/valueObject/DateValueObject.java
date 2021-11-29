package es.jaime.gateway._shared.domain.valueObject;


import java.time.LocalDateTime;
import java.util.Objects;

public class DateValueObject extends ValueObject{
    private final LocalDateTime value;
    private final String valueString;

    public DateValueObject(LocalDateTime value) {
        this.value = value;
        this.valueString = value.toString();
    }

    public DateValueObject(String value){
        this.valueString = value;
        this.value = LocalDateTime.parse(value);
    }

    public LocalDateTime value(){
        return this.value;
    }

    public String valueString(){
        return this.valueString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateValueObject that = (DateValueObject) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
