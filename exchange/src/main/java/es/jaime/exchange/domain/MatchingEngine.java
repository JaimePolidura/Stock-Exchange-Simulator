package es.jaime.exchange.domain;

public interface MatchingEngine {
    void enqueue(Order order);
}
