package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ActiveOrderDate extends StringValueObject {
    public ActiveOrderDate(String value) {
        super(value);
    }

    public ActiveOrderDate(){
        super(null);
    }

    public static ActiveOrderDate now (){
        return new ActiveOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public static ActiveOrderDate of (String date){
        return new ActiveOrderDate(date);
    }
}
