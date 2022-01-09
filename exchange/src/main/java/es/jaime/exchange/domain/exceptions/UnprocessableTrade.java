package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.models.orders.ExecutionOrder;

public class UnprocessableTrade extends DomainException{
    public UnprocessableTrade(ExecutionOrder order) {
        super("Unprocessable trade, no operation was executed. Try later", order);
    }
}
