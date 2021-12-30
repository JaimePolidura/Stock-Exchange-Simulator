package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.orders.SellOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

@AllArgsConstructor
public class SellOrderArrivedEvent extends DomainEvent{
    @Getter private final SellOrder sellOrder;

    public static SellOrderArrivedEvent fromJSON(JSONObject jsonObject){
        return new SellOrderArrivedEvent(new SellOrder(
                jsonObject.getString("orderId"),
                jsonObject.getString("clientId"),
                jsonObject.getString("date"),
                jsonObject.getDouble("executionPrice"),
                jsonObject.getInt("quantity"),
                jsonObject.getString("tradeId")
        ));
    }
}
