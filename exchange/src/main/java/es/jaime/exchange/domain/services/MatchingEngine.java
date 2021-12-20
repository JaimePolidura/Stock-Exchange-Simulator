package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.Order;

import java.util.Queue;

public interface MatchingEngine extends Runnable {
    void enqueue(Order order);

    Queue<Order> getBuyOrdersQueue();
    Queue<Order> getSellOrdersQueue();

    void stop();
}
