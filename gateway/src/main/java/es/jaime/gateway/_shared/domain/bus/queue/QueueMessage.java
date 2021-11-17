package es.jaime.gateway._shared.domain.bus.queue;

import java.util.Map;

public interface QueueMessage {
    Map<String, Object> toPrimitives();
}
