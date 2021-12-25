package es.jaime.exchange.domain.models.messages;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ErrorOrderMessage extends Message{
    private static final MessageType messageType = MessageType.ERROR_ORDER;

    private final String message;
    private final String clientId;
    private final String orderId;

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public List<String> getTo() {
        return List.of(clientId);
    }

    @Override
    public Map<String, Object> getBody() {
        return Map.of(
                "message", message,
                "orderId", orderId
        );
    }
}
