package es.jaime.gateway.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.messagePublisher.CommandMessage;
import es.jaime.gateway.orders._shared.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SendBuyOrderMessage implements CommandMessage {
    @Getter private final OrderID orderID;
    @Getter private final OrderClientID clientID;
    @Getter private final OrderDate date;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderTicker ticker;
    @Getter private final OrderType type;

    @Override
    public String name() {
        return "new-order-buy";
    }

    @Override
    public Map<String, Object> body() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("clientId", clientID.value());
            put("date", date.value());
            put("executionPrice", executionPrice.value());
            put("quantity", quantity.value());
            put("ticker", ticker.value());
            put("type", type.valueString());
        }};
    }
}
