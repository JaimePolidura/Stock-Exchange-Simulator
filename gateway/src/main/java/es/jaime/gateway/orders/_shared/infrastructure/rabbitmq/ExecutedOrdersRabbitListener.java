package es.jaime.gateway.orders._shared.infrastructure.rabbitmq;

import es.jaime.gateway._shared.domain.ExecutedOrderTypes;
import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway.orders._shared.domain.events.BuyOrderExecuted;
import es.jaime.gateway.orders._shared.domain.events.OrderExecuted;
import es.jaime.gateway.orders._shared.domain.events.SellOrderExecuted;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"rabbitmq-starter"})
public class ExecutedOrdersRabbitListener {
    private final EventBus eventBus;

    public ExecutedOrdersRabbitListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "sxs.executed-orders.gateway")
    public void on (String jsonString){
        OrderExecuted event = buildEventFromJSON(new JSONObject(jsonString));

        this.eventBus.publish(event);
    }

    private OrderExecuted buildEventFromJSON(JSONObject jsonObject){
        String type = jsonObject.getString("type");

        return type.equalsIgnoreCase(ExecutedOrderTypes.EXECUTED_BUY_ORDER.getType()) ?
                buildBuyOrderEvent(jsonObject) :
                buildSellOrderEvent(jsonObject);
    }

    private BuyOrderExecuted buildBuyOrderEvent(JSONObject jsonObject) {
        JSONObject body = jsonObject.getJSONObject("body");

        return new BuyOrderExecuted(getOrderId(body), getClientId(jsonObject), getTicker(body),
                getExecutionPrice(body), getQuantity(body), getDate(body));
    }

    private SellOrderExecuted buildSellOrderEvent(JSONObject jsonObject) {
        JSONObject body = jsonObject.getJSONObject("body");

        return new SellOrderExecuted(getOrderId(body), getClientId(jsonObject), getTicker(body),
                getExecutionPrice(body), getQuantity(body), getDate(body), getTradeId(body));
    }

    private String getOrderId(JSONObject jsonObject) {
        return jsonObject.getString("orderId");
    }

    private String getClientId(JSONObject jsonObject){
        return String.valueOf(jsonObject.getJSONArray("to").get(0));
    }

    private String getTicker(JSONObject jsonObject){
        return jsonObject.getString("ticker");
    }

    private double getExecutionPrice(JSONObject jsonObject){
        return jsonObject.getDouble("executionPrice");
    }

    private int getQuantity(JSONObject jsonObject){
        return jsonObject.getInt("quantity");
    }

    private String getDate(JSONObject jsonObject){
        return jsonObject.getString("date");
    }

    private String getTradeId(JSONObject jsonObject){
        return jsonObject.getString("tradeId");
    }
}
