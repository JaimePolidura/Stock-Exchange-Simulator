package es.jaime.gateway._shared.domain.queue;

public interface QueuePublisher {
    void enqueue(QueueMessage queueMessage);
}
