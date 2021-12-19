package es.jaime.gateway.activeorders.executeorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway._shared.domain.queue.QueueMessage;
import es.jaime.gateway.activeorders._shared.domain.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ExecuteOrderCommand implements Command, QueueMessage {
    @Getter private final ActiveOrderID orderID;
    @Getter private final ActiveOrderClientID clientID;
    @Getter private final ActiveOrderDate date;
    @Getter private final ActiveOrderExecutionPrice executionPrice;
    @Getter private final ActiveOrderQuantity quantity;
    @Getter private final ActiveOrderTicker ticker;
    @Getter private final ActiveOrderType type;

    public ExecuteOrderCommand(String clientId, int quantity, String ticker, String orderType, double executionPrice) {
        this.orderID = ActiveOrderID.generate();
        this.clientID = ActiveOrderClientID.of(clientId);
        this.quantity = ActiveOrderQuantity.of(quantity);
        this.ticker = ActiveOrderTicker.of(ticker);
        this.type = ActiveOrderType.of(orderType);
        this.date = ActiveOrderDate.now();
        this.executionPrice = ActiveOrderExecutionPrice.of(executionPrice);
    }

    @Override
    @SneakyThrows
    public String toJson() {
        return new ObjectMapper().writeValueAsString(toPrimitives());
    }

    @Override
    public String routingKey() {
        return String.format("sxs.new-orders.%s", this.ticker.value());
    }

    private Map<String, Serializable> toPrimitives() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("clientId", clientID.value());
            put("date", date.value().toString());
            put("executionPrice", executionPrice.value());
            put("quantity", quantity.value());
            put("ticker", ticker.value());
            put("type", type.valueString());
        }};
    }
}
