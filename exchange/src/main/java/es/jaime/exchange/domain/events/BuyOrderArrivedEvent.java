package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

@AllArgsConstructor
public class BuyOrderArrivedEvent extends DomainEvent{
    @Getter private final BuyOrder buyOrder;

    public static BuyOrderArrivedEvent fromJSON(JSONObject jsonObject){
        return new BuyOrderArrivedEvent(new BuyOrder(
                jsonObject.getString("orderId"),
                jsonObject.getString("clientId"),
                jsonObject.getString("date"),
                jsonObject.getDouble("executionPrice"),
                jsonObject.getInt("quantity"),
                jsonObject.getString("ticker")
        ));
    }
}