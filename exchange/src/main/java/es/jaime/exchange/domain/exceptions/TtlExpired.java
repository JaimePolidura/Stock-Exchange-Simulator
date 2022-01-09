package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.models.orders.ExecutionOrder;

public class TtlExpired extends DomainException{
    public TtlExpired(ExecutionOrder order) {
        super("TTL Expired!", order);
    }
}
