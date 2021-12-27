package es.jaime.gateway.orders.executeorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway._shared.domain.queue.QueueMessage;
import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ExecuteOrderCommand implements Command, QueueMessage {
    @Getter private final OrderID orderID;
    @Getter private final OrderClientID clientID;
    @Getter private final OrderDate date;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderTicker ticker;
    @Getter private final OrderType type;

    public ExecuteOrderCommand(String clientId, int quantity, String ticker, String orderType, double executionPrice) {
        this.orderID = OrderID.generate();
        this.clientID = OrderClientID.of(clientId);
        this.quantity = OrderQuantity.of(quantity);
        this.ticker = OrderTicker.of(ticker);
        this.type = OrderType.of(orderType);
        this.date = OrderDate.now();
        this.executionPrice = OrderExecutionPrice.of(executionPrice);
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
