package es.jaime.exchange.domain.models.messages;

import es.jaime.exchange.domain.models.orders.OrderType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BuyOrderExecutedMessage extends Message{
    private static final MessageType type = MessageType.EXECUTED_BUY_ORDER;
    private final String orderId;
    private final String clientId;
    private final String ticker;
    private final double executionPrice;
    private final int quantity;
    private final String date;
    private final OrderType orderType;

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
                "orderId", orderId,
                "type", orderType.toString()
        );
    }
}
