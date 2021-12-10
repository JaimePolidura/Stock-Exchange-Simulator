package es.jaime.exchange.domain;

public interface QueuePublisher {
    void enqueue(QueueMessage queueMessage);
}
