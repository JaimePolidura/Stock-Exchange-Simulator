package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.EventBus;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OnNewBuyOrder {
    private final EventBus eventBus;

    public OnNewBuyOrder(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "${buy}")
    public void on(String bodyString){
        JSONObject json = new JSONObject(bodyString);

        this.eventBus.publish(BuyOrderArrivedEvent.fromJSON(json));
    }
}
