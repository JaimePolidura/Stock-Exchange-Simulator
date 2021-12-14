package es.jaime.exchange.domain;

public interface TradeProcessor {
    void process(Order buyOrder, Order sellOrder) throws Exception;
}
