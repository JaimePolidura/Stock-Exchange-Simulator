package es.jaime.gateway.activeorders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ActiveOrder extends Aggregate {
    private ActiveOrderID activeorderId;
    private ActiveOrderClientID clientId;
    private ActiveOrderDate date;
    private ActiveOrderQuantity quantity;
    private ActiveOrderTicker ticker;
    private ActiveOrderType type;
    private ActiveOrderExecutionPrice executionPrice;
    private ActiveOrderStatus status;

    private ActiveOrder () {}

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("activeorderId", activeorderId.value());
            put("clientId", clientId.value());
            put("date", date.value());
            put("quantity", quantity.value());
            put("type", type);
            put("executionPrice", executionPrice.value());
            put("status", status.valueString());
        }};
    }

    @Override
    public ActiveOrder fromPrimitives(Map<String, Object> values) {
        this.activeorderId = ActiveOrderID.of((String) values.get("activeorderId"));
        this.clientId = ActiveOrderClientID.of((String) values.get("clientId"));
        this.date = ActiveOrderDate.of(String.valueOf(values.get("date")));
        this.quantity = ActiveOrderQuantity.of((Integer) values.get("quantity"));
        this.ticker = ActiveOrderTicker.of(String.valueOf(values.get("ticker")));
        this.type = ActiveOrderType.of(String.valueOf(values.get("type")));
        this.executionPrice = ActiveOrderExecutionPrice.of(Double.parseDouble(String.valueOf(values.get("executionPrice"))));
        this.status = ActiveOrderStatus.of(String.valueOf(values.get("status")));

        return this;
    }

    private UUID primitiveObjectToUUID(Map<String, Object> values, String key){
        return UUID.fromString(String.valueOf(values.get(key)));
    }

    public ActiveOrderID activeorderId() {
        return activeorderId;
    }

    public ActiveOrderClientID clientId() {
        return clientId;
    }

    public ActiveOrderDate date() {
        return date;
    }

    public ActiveOrderQuantity quantity() {
        return quantity;
    }

    public ActiveOrderTicker ticker() {
        return ticker;
    }

    public ActiveOrderType type() {
        return type;
    }

    public ActiveOrderExecutionPrice executionPrice() {
        return executionPrice;
    }

    public ActiveOrderStatus status() {
        return status;
    }
}
