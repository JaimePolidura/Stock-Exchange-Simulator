package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.models.orders.Order;

public class TtlExpired extends DomainException{
    public TtlExpired(Order order) {
        super("TTL Expired!", order);
    }
}
