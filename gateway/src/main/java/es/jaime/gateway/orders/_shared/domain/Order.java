package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public final class Order extends Aggregate {
    @Getter private OrderID orderId;
    @Getter private OrderTicker ticker;
    @Getter private OrderClientID clientId;
    @Getter private OrderDate date;
    @Getter private OrderQuantity quantity;
    @Getter private OrderType type;
    @Getter private OrderExecutionPrice executionPrice;

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
}
