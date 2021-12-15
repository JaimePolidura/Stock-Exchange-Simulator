package es.jaime.exchange.domain;

public class UnprocessableTrade extends DomainException{
    public UnprocessableTrade(String msg) {
        super(msg);
    }
}
