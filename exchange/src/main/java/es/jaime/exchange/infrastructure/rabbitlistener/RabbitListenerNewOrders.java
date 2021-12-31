package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.EventBus;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitListenerNewOrders {
    private final EventBus eventBus;

    public RabbitListenerNewOrders(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "${queue}")
    public void on(String bodyString){
        JSONObject json = new JSONObject(bodyString);

        this.eventBus.publish(BuyOrderArrivedEvent.fromJSON(json));
    }
}
