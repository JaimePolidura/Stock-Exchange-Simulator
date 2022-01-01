package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.SellOrderArrivedEvent;
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
        JSONObject bodyJson = json.getJSONObject("body");

        System.out.println("Recieved order: " + json);

        //TODO improve with reflections
        String commandName = json.getString("name");

        if(commandName.equalsIgnoreCase("new-order-buy")){
            this.eventBus.publish(BuyOrderArrivedEvent.fromJSON(bodyJson));
        }else if(commandName.equalsIgnoreCase("new-order-sell")){
            this.eventBus.publish(SellOrderArrivedEvent.fromJSON(bodyJson));
        }

    }
}
