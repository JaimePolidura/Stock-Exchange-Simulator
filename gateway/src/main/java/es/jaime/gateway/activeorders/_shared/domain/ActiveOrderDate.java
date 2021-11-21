package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.DateValueObject;

import java.time.LocalDateTime;

public final class ActiveOrderDate extends DateValueObject {
    public ActiveOrderDate(LocalDateTime value) {
        super(value);
    }

    public static ActiveOrderDate now (){
        return new ActiveOrderDate(LocalDateTime.now());
    }

    public static ActiveOrderDate of (String date){
        return new ActiveOrderDate(LocalDateTime.parse(date));
    }
}
