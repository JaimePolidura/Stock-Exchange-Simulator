package es.jaime.gateway._shared.domain.valueObject;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateValueObject extends ValueObject{
    private final LocalDateTime value;


    public DateValueObject(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value(){
        return this.value;
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
