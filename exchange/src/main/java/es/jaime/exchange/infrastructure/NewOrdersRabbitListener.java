package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.SellOrderArrivedEvent;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NewOrdersRabbitListener {
    private final EventBus eventBus;

    public NewOrdersRabbitListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Value("${queue}")
    private String queue;

    @SneakyThrows
    @RabbitListener(queues = "${queue}")
    public void listener(String incomeOrder){
        System.out.println("Order received " + incomeOrder);
        JSONObject json = new JSONObject(incomeOrder);

        if(isBuyOrder(json)){
            this.eventBus.publish(BuyOrderArrivedEvent.fromJSON(json));
        }else{
            this.eventBus.publish(SellOrderArrivedEvent.fromJSON(json));
        }
    }

    private boolean isBuyOrder(JSONObject json){
        return !json.has("tradeId");
    }
}
