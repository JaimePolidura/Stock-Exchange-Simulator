package es.jaime.exchange.domain.models.messages;

import java.util.List;
import java.util.Map;

public abstract class Message {
    public abstract MessageType getMessageType();
    public abstract List<String> getTo();
    public abstract Map<String, Object> getBody();

    public Map<String, Object> toPrimitives(){
        return Map.of(
                "type", getMessageType().getType(),
                "to", getTo(),
                "body", getBody()
        );
    }
}

