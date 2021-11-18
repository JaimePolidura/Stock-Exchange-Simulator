package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.orders._shared.infrastrocture.converters.*;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "activeorders")
@AllArgsConstructor
public final class ActiveOrder extends Aggregate {
    @Id
    @Convert(converter = ActiveOrderIdConverter.class)
    private ActiveOrderID activeorderId;

    @Column
    @Convert(converter = ActiveOrderIdConverter.class)
    private ActiveOrderClientID clientId;

    @Column
    @Convert(converter = ActiveOrderDateConverter.class)
    private ActiveOrderDate date;

    @Column
    @Convert(converter = ActiveOrderQuantityConverter.class)
    private ActiveOrderQuantity quantity;

    @Column
    @Convert(converter = ActiveOrderTickerConverter.class)
    private ActiveOrderTicker ticker;

    @Column
    private ActiveOrderType type;

    @Column
    @Convert(converter = ActiveOrderExecutionDateConverter.class)
    private ActiveOrderExecutionPrice executionPrice;

    public ActiveOrder(){}

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("activeorderId", activeorderId.value());
            put("clientId", clientId.value());
            put("date", date.value());
            put("quantity", quantity.value());
            put("type", type);
            put("executionPrice", executionPrice.value());
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

    public ActiveOrderID getActiveorderId() {
        return activeorderId;
    }

    public void setActiveorderId(ActiveOrderID activeorderId) {
        this.activeorderId = activeorderId;
    }

    public ActiveOrderClientID getClientId() {
        return clientId;
    }

    public void setClientId(ActiveOrderClientID clientId) {
        this.clientId = clientId;
    }

    public ActiveOrderDate getDate() {
        return date;
    }

    public void setDate(ActiveOrderDate date) {
        this.date = date;
    }

    public ActiveOrderQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(ActiveOrderQuantity quantity) {
        this.quantity = quantity;
    }

    public ActiveOrderTicker getTicker() {
        return ticker;
    }

    public void setTicker(ActiveOrderTicker ticker) {
        this.ticker = ticker;
    }

    public ActiveOrderType getType() {
        return type;
    }

    public void setType(ActiveOrderType type) {
        this.type = type;
    }

    public ActiveOrderExecutionPrice getExecutionPrice() {
        return executionPrice;
    }

    public void setExecutionPrice(ActiveOrderExecutionPrice executionPrice) {
        this.executionPrice = executionPrice;
    }
}
