package es.jaime.gateway.orders.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.messagePublisher.CommandMessage;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SendBuyOrderMessage implements CommandMessage {
    @Getter private final OrderId orderID;
    @Getter private final OrderClientId clientID;
    @Getter private final OrderDate date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final OrderTypeId type;

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
            put("executionPrice", executionPrice);
            put("quantity", quantity);
            put("ticker", ticker);
            put("type", type.value());
        }};
    }
}
