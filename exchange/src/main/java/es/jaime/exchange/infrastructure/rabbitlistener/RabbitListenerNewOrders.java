package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.SellOrderArrivedEvent;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RabbitListenerNewOrders implements CommandLineRunner {
    private static final String CONSUMER_NAME = "new-orders-consumer";

    private final ExchangeConfiguration configuration;
    private final RabbitListenerEndpointRegistry registry;
    private final EventBus eventBus;

    public RabbitListenerNewOrders(ExchangeConfiguration configuration, RabbitListenerEndpointRegistry registry,
                                   EventBus eventBus) {
        this.configuration = configuration;
        this.registry = registry;
        this.eventBus = eventBus;
    }

    @Override
    public void run(String... args) {
        AbstractMessageListenerContainer container = (AbstractMessageListenerContainer) registry.getListenerContainer(CONSUMER_NAME);

        container.addQueueNames(configuration.queueNewOrders());

        container.start();
    }

    @RabbitListener(id = CONSUMER_NAME, autoStartup = "false")
    public void listen(String bodyString) {
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
