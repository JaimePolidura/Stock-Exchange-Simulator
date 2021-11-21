package es.jaime.gateway.activeorders.executeorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jaime.gateway._shared.domain.bus.command.Command;
import es.jaime.gateway._shared.domain.bus.queue.QueueMessage;
import es.jaime.gateway.activeorders._shared.domain.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ExecuteOrderCommand implements Command, QueueMessage {
    @Getter private final ActiveOrderID orderID;
    @Getter private final ActiveOrderClientID clientID;
    @Getter private final ActiveOrderDate date;
    @Getter private final ActiveOrderExecutionPrice executionPrice;
    @Getter private final ActiveOrderQuantity quantity;
    @Getter private final ActiveOrderTicker ticker;
    @Getter private final ActiveOrderType type;

    public ExecuteOrderCommand(String clientId, int quantity, String ticker, String orderType, double executionPrice) {
        this.orderID = ActiveOrderID.of(UUID.randomUUID());
        this.clientID = ActiveOrderClientID.of(UUID.fromString(clientId));
        this.quantity = ActiveOrderQuantity.of(quantity);
        this.ticker = ActiveOrderTicker.of(ticker);
        this.type = ActiveOrderType.valueOf(orderType);
        this.date = ActiveOrderDate.now();
        this.executionPrice = ActiveOrderExecutionPrice.of(executionPrice);
    }

    @Override
    @SneakyThrows
    public String toJson() {
        return new ObjectMapper().writeValueAsString(toPrimitives());
    }

    private Map<String, Serializable> toPrimitives() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("clientID", clientID.value());
            put("date", date.value().toString());
            put("executionPrice", executionPrice.value());
            put("quantity", quantity.value());
            put("ticker", ticker.value());
            put("type", type.toString());
        }};
    }
}
