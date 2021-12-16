package es.jaime.exchange.application;

import es.jaime.exchange.domain.Order;
import es.jaime.exchange.domain.QueueMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
public class ExecutedOrderQueueMessage implements QueueMessage {
    @Getter private final String ticker;
    @Getter private final LocalDateTime date;
    @Getter private final int quantity;
    @Getter private final double executionPrice;
    @Getter private final ClientData buyerData;
    @Getter private final ClientData sellerData;

    public static ExecutedOrderQueueMessage create(Order buyOrder, Order sellOrder, double executionPrice, int quantity){
        return new ExecutedOrderQueueMessage(
                buyOrder.getTicker(),
                LocalDateTime.now(),
                quantity,
                executionPrice,
                new ClientData(buyOrder.getOrderId(), buyOrder.getClientId()),
                new ClientData(sellOrder.getOrderId(), sellOrder.getClientId())
        );
    }

    @Override
    public String toJson(){
        return new JSONObject(Map.of(
                "ticker: ", ticker,
                "date: ", date.toString(),
                "quantity: ", quantity,
                "executionPrice: ", executionPrice,
                "buyer: ", Map.of(
                        "orderId: ", buyerData.orderId,
                        "clientId: ", buyerData.clientId
                ),
                "seller: ", Map.of(
                        "orderId: ", sellerData.orderId,
                        "clientId: ", sellerData.clientId
                )
        )).toString();
    }

    @AllArgsConstructor
    private static final class ClientData {
        @Getter private final String orderId;
        @Getter private final String clientId;
    }
}
