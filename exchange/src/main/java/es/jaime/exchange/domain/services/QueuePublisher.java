package es.jaime.exchange.domain.services;

public interface QueuePublisher {
    void enqueue(String exchange, String queue, QueueMessage queueMessage);
}
