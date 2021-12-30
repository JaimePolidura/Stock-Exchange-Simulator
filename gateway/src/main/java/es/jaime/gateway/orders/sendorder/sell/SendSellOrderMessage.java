package es.jaime.gateway.orders.sendorder.sell;


import com.fasterxml.jackson.databind.ObjectMapper;
import es.jaime.gateway._shared.domain.messagePublisher.Message;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.ordertypes.domain.OrderTypeName;
import es.jaime.gateway.trades._shared.domain.TradeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SendSellOrderMessage implements Message {
    @Getter private final OrderID orderID;
    @Getter private final TradeId tradeId;
    @Getter private final OrderClientID clientID;
    @Getter private final OrderDate date;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderTicker ticker;
    @Getter private final OrderType type;

    @Override
    @SneakyThrows
    public String toJson() {
        return new ObjectMapper().writeValueAsString(toPrimitives());
    }

    @Override
    public String routingKey() {
        return RabbitMQNameFormatter.routingKeyNewOrders(OrderTypeName.sell(), ticker);
    }

    private Map<String, Serializable> toPrimitives() {
        return new HashMap<>(){{
            put("orderId", orderID.value());
            put("tradeId", tradeId.value());
            put("clientId", clientID.value());
            put("date", date.value());
            put("executionPrice", executionPrice.value());
            put("quantity", quantity.value());
            put("ticker", ticker.value());
            put("type", type.valueString());
        }};
    }
}
