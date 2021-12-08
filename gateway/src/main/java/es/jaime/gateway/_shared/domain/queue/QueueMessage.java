package es.jaime.gateway._shared.domain.queue;

import java.io.Serializable;

public interface QueueMessage extends Serializable {
    String toJson();
    String routingKey();
}
