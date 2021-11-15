package es.jaime.gateway._shared.domain.valueObject;

import java.time.LocalDateTime;

public class DateValueObject extends ValueObject{
    private final LocalDateTime value;


    public DateValueObject(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value(){
        return this.value;
    }
}
