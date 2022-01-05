package es.jaime.gateway.orders.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.messages.CommandMessage;
import es.jaime.gateway._shared.domain.messages.MessageNames;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.trades._shared.domain.TradeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SendSellOrderMessage implements CommandMessage {
    @Getter private final OrderId orderID;
    @Getter private final TradeId tradeId;
    @Getter private final OrderClientId clientID;
    @Getter private final OrderDate date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final OrderTypeId type;

    @Override
    public MessageNames name() {
        return MessageNames.NEW_ORDER_SELL;
    }

    @Override
    public Map<String, Object> body() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("tradeId", tradeId.value());
            put("clientId", clientID.value());
            put("date", date.value());
            put("executionPrice", executionPrice);
            put("quantity", quantity);
            put("ticker", ticker);
            put("type", type.value());
        }};
    }
}
