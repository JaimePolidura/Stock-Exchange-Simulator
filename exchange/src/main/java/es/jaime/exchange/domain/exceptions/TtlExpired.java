package es.jaime.exchange.domain.exceptions;

import es.jaime.exchange.domain.models.orders.TradeOrder;

public class TtlExpired extends DomainException{
    public TtlExpired(TradeOrder order) {
        super("TTL Expired!", order);
    }
}
