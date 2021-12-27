package es.jaime.gateway._shared.domain.messagePublisher;

import java.io.Serializable;

public interface Message extends Serializable {
    String toJson();
    String routingKey();
}
