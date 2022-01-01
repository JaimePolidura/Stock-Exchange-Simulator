package es.jaime.gateway.orders._shared.infrastructure.rabbitmq;


import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway.orders._shared.domain.events.SellOrderExecuted;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

//@Service
//@DependsOn({"rabbitmq-starter"})
public class OnOrderExecutedSellRabbitListener {
    private final EventBus eventBus;

    public OnOrderExecutedSellRabbitListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "sxs.exchange.events.gateway.order-executed-sell")
    public void on(String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject body = jsonObject.getJSONObject("body");
        JSONObject meta = jsonObject.getJSONObject("meta");

        this.eventBus.publish(new SellOrderExecuted(
                body.getString("orderId"),
                String.valueOf(meta.getJSONArray("to").get(0)),
                body.getString("ticker"),
                body.getDouble("executionPrice"),
                body.getInt("quantity"),
                body.getString("date"),
                body.getString("tradeId")
        ));
    }
}
