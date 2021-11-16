package es.jaime.gateway.orders._shared.domain;

import java.util.Map;

public interface QueueMessage {
    Map<String, Object> toPrimitives();
}
