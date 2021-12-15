package es.jaime.exchange.domain;

public interface MatchingEngine extends Runnable{
    void enqueue(Order order);
}
