package es.jaime.gateway.orders._shared.domain;

import java.util.Map;

public interface QueuePublisher {
    void enqueue(Map<String, Object> message);
}
