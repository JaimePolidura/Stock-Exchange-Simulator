package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.Order;

public class UnprocessableTrade extends DomainException{
    public UnprocessableTrade(Order order) {
        super("Unprocessable trade, no operation was executed. Try later", order);
    }
}