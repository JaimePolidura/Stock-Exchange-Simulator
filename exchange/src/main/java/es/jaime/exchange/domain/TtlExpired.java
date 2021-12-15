package es.jaime.exchange.domain;

public class TtlExpired extends DomainException{
    private final Order order;

    public TtlExpired(Order order) {
        super("TTL Expired!", orderException);
        this.order = order;
    }
}
