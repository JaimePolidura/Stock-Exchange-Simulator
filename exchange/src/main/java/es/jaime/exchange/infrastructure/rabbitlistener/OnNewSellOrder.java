package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.SellOrderArrivedEvent;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OnNewSellOrder {
    private final EventBus eventBus;

    public OnNewSellOrder(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "${sell}")
    public void on(String bodyString){
        JSONObject json = new JSONObject(bodyString);

        this.eventBus.publish(SellOrderArrivedEvent.fromJSON(json));
    }
}
