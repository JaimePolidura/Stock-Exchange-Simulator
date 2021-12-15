package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.Order;
import lombok.Getter;

public class DomainException extends RuntimeException{
    @Getter private final Order orderException;

    public DomainException(String msg, Order orderException) {
        super(msg);
        this.orderException = orderException;
    }
}
