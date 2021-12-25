package es.jaime.exchange.domain.models.messages;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ExecutedOrderMessage extends Message{
    private static final MessageType type = MessageType.EXECUTED_ORDER;
    private final String orderId;
    private final String clientId;
    private final String ticker;
    private final double executionPrice;
    private final int quantity;
    private final String date;

    @Override
    public MessageType getMessageType() {
        return type;
    }

    @Override
    public List<String> getTo() {
        return List.of(clientId);
    }

    @Override
    public Map<String, Object> getBody() {
        return Map.of(
                "ticker", ticker,
                "executionPrice", executionPrice,
                "quantity", quantity,
                "date", date,
                "orderId", orderId
        );
    }
}
