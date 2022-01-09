package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import lombok.Getter;

public class DomainException extends Exception{
    @Getter private final ExecutionOrder orderException;

    public DomainException(String msg, ExecutionOrder orderException) {
        super(msg);
        this.orderException = orderException;
    }
}
