package es.jaime.exchange.domain.models.messages;

import es.jaime.exchange.domain.models.orders.OrderType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public final class SellOrderExecutedMessage implements EventMessage {
    private final String orderId;
    private final String clientId;
    private final String tradeId;
    private final double executionPrice;
    private final int quantity;
    private final String date;
    private final OrderType orderType;
    private final String ticker;

    @Override
    public String name() {
        return "order-executed-sell";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "tradeId", tradeId,
                "executionPrice", executionPrice,
                "quantity", quantity,
                "date", date,
                "orderId", orderId,
                "type", orderType.toString(),
                "ticker", ticker
        );
    }

    @Override
    public Map<String, Object> meta() {
        return Map.of("to", List.of(clientId));
    }
}
