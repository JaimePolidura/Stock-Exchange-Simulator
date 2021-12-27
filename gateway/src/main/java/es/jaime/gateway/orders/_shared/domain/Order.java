package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class Order extends Aggregate {
    private OrderID orderId;
    private OrderTicker ticker;
    private OrderClientID clientId;
    private OrderDate date;
    private OrderQuantity quantity;
    private OrderType type;
    private OrderExecutionPrice executionPrice;

    private Order() {}

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("orderId", orderId.value());
            put("clientId", clientId.value());
            put("ticker", ticker.value());
            put("date", date.value());
            put("quantity", quantity.value());
            put("type", type);
            put("executionPrice", executionPrice.value());
        }};
    }

    @Override
    public Order fromPrimitives(Map<String, Object> values) {
        this.orderId = OrderID.of((String) values.get("orderId"));
        this.clientId = OrderClientID.of((String) values.get("clientId"));
        this.date = OrderDate.of(String.valueOf(values.get("date")));
        this.quantity = OrderQuantity.of((Integer) values.get("quantity"));
        this.ticker = OrderTicker.of(String.valueOf(values.get("ticker")));
        this.type = OrderType.of(String.valueOf(values.get("type")));
        this.executionPrice = OrderExecutionPrice.of(Double.parseDouble(String.valueOf(values.get("executionPrice"))));

        return this;
    }

    private UUID primitiveObjectToUUID(Map<String, Object> values, String key){
        return UUID.fromString(String.valueOf(values.get(key)));
    }

    public OrderID orderId() {
        return orderId;
    }

    public OrderClientID clientId() {
        return clientId;
    }

    public OrderDate date() {
        return date;
    }

    public OrderQuantity quantity() {
        return quantity;
    }

    public OrderTicker ticker() {
        return ticker;
    }

    public OrderType type() {
        return type;
    }

    public OrderExecutionPrice executionPrice() {
        return executionPrice;
    }
}
