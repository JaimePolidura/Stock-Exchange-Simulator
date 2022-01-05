package es.jaime.exchange.domain.models.messages.messages;

import es.jaime.exchange.domain.models.messages.EventMessage;
import es.jaime.exchange.domain.models.messages.MessageNames;
import es.jaime.exchange.domain.models.orders.OrderType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BuyOrderExecutedMessage implements EventMessage {
    private final String orderId;
    private final String clientId;
    private final String ticker;
    private final double executionPrice;
    private final int quantity;
    private final String date;
    private final OrderType orderType;

    @Override
    public MessageNames name() {
        return MessageNames.EXECUTED_BUY_ORDER;
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "ticker", ticker,
                "executionPrice", executionPrice,
                "quantity", quantity,
                "date", date,
                "orderId", orderId,
                "type", orderType.toString()
        );
    }

    @Override
    public Map<String, Object> meta() {
        return Map.of("to", List.of(clientId));
    }
}
