package es.jaime.exchange.domain.models.messages;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ErrorOrderMessage implements EventMessage {
    private final String message;
    private final String clientId;
    private final String orderId;

    @Override
    public String name() {
        return "order-error";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "message", message,
                "orderId", orderId
        );
    }

    @Override
    public Map<String, Object> meta() {
        return Map.of("to", List.of(clientId));
    }
}
