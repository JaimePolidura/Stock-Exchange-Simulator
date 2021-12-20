package es.jaime.exchange.domain;

public interface QueuePublisher {
    void enqueue(String exchange, String queue, QueueMessage queueMessage);
}
