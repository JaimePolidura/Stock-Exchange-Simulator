package es.jaime.exchange.domain.models.messages.messages;

import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.messages.MessageTypes;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public final class OrderCancelledMessage implements Message {
    private final String clientId;
    private final String orderId;
    private final String orderIdCancelled;

    @Override
    public MessageTypes type() {
        return MessageTypes.EVENT;
    }

    @Override
    public MessageName name() {
        return MessageName.ORDER_CANCELLED;
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "clientId", this.clientId,
                "orderId", this.orderId,
                "orderIdCancelled", this.orderIdCancelled
        );
    }

    @Override
    public Map<String, Object> meta() {
        return Map.of("to", List.of(this.clientId));
    }
}
