package es.jaime.gateway.orders.newmodel._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OrderDate extends StringValueObject {
    public OrderDate(String value) {
        super(value);
    }

    public OrderDate(){
        super(null);
    }

    public static OrderDate now (){
        return new OrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public static OrderDate of (String date){
        return new OrderDate(date);
    }
}
