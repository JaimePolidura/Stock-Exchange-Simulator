package es.jaime.gateway.orders._shared.infrastructure.rabbitmq;

import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway.orders._shared.domain.*;
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
        JSONObject body = jsonObject.getJSONObject("body");

        String orderId = body.getString("orderId");
        String clientId = String.valueOf(jsonObject.getJSONArray("to").get(0));
        String ticker = body.getString("ticker");
        double executionPrice = body.getDouble("executionPrice");
        int quantity = body.getInt("quantity");
        String date = body.getString("date");
        OrderTypeValues orderType = OrderTypeValues.valueOf(body.getString("type"));

        return orderType == OrderTypeValues.BUY ?
                new BuyOrderExecuted(orderId, clientId, ticker, executionPrice, quantity, date, orderType) :
                new SellOrderExecuted(orderId, clientId, ticker, executionPrice, quantity, date, orderType);
    }
}
