package es.jaime.exchange.domain.models.messages;

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
    public String name() {
        return "order-executed-buy";
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
