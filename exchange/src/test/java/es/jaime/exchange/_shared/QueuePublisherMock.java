package es.jaime.exchange._shared;

import es.jaime.exchange.domain.QueueMessage;
import es.jaime.exchange.domain.QueuePublisher;

import java.util.LinkedList;
import java.util.Queue;

public class QueuePublisherMock implements QueuePublisher {
    private final Queue<QueueMessage> queue;

    public QueuePublisherMock(){
        this.queue = new LinkedList<>();
    }

    @Override
    public void enqueue(String exchange, String queue, QueueMessage queueMessage) {
        this.queue.offer(queueMessage);
    }

    public Queue<QueueMessage> getQueue() {
        return queue;
    }
}
