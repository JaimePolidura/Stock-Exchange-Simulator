package es.jaime.gateway.orders.pendingorder.execution.sell.send;

import es.jaime.gateway._shared.domain.messages.CommandMessage;
import es.jaime.gateway._shared.domain.messages.MessageNames;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.OrderPositionIdToSell;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SendSellOrderMessage implements CommandMessage {
    @Getter private final OrderId orderID;
    @Getter private final OrderPositionIdToSell orderIdToSell;
    @Getter private final OrderClientId clientID;
    @Getter private final OrderDate date;
    @Getter private final OrderPriceToExecute executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderTicker ticker;
    @Getter private final PendingOrderType type;

    @Override
    public MessageNames name() {
        return MessageNames.NEW_ORDER_SELL;
    }

    @Override
    public Map<String, Object> body() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("positionId", orderIdToSell.value());
            put("clientId", clientID.value());
            put("date", date.value());
            put("executionPrice", executionPrice.value());
            put("quantity", quantity.value());
            put("ticker", ticker);
            put("type", type.value());
        }};
    }
}
