package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ActiveOrder extends Aggregate {
    @Getter private ActiveOrderID activeorderId;
    @Getter private ActiveOrderClientID clientId;
    @Getter private ActiveOrderDate date;
    @Getter private ActiveOrderQuantity quantity;
    @Getter private ActiveOrderTicker ticker;
    @Getter private ActiveOrderType type;
    @Getter private ActiveOrderExecutionPrice executionPrice;

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("activeorderId", activeorderId.value());
            put("clientId", clientId.value());
            put("date", date.value());
            put("quantity", quantity.value());
            put("type", type);
            put("executionPrice", executionPrice.getValue());
        }};
    }

    @Override
    public ActiveOrder fromPrimitives(Map<String, Object> values) {
        this.activeorderId = ActiveOrderID.of(primitiveObjectToUUID(values, "activeorderId"));
        this.clientId = ActiveOrderClientID.of(primitiveObjectToUUID(values, "clientId"));
        this.date = ActiveOrderDate.of(String.valueOf(values.get("date")));
        this.quantity = ActiveOrderQuantity.of((Integer) values.get("quantity"));
        this.ticker = ActiveOrderTicker.of(String.valueOf(values.get("ticker")));
        this.type = ActiveOrderType.valueOf(String.valueOf(values.get("executionType")));
        this.executionPrice = ActiveOrderExecutionPrice.of(Double.parseDouble(String.valueOf(values.get("executionPrice"))));

        return this;
    }

    private UUID primitiveObjectToUUID(Map<String, Object> values, String key){
        return UUID.fromString(String.valueOf(values.get(key)));
    }
}
