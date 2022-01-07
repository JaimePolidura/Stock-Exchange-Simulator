package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.events.BuyOrderArrivedEvent;
import es.jaime.exchange.domain.events.DomainEvent;
import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.SellOrderArrivedEvent;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.String.valueOf;

@Component
@Order(2)
public class RabbitListenerNewOrders implements CommandLineRunner {
    private static final String CONSUMER_NAME = "new-orders-consumer";

    private final ExchangeConfiguration configuration;
    private final DomainEventListenersInformation eventListenersInformation;
    private final RabbitListenerEndpointRegistry registry;
    private final EventBus eventBus;

    public RabbitListenerNewOrders(ExchangeConfiguration configuration, DomainEventListenersInformation eventListenersInformation,
                                   RabbitListenerEndpointRegistry registry, EventBus eventBus) {
        this.configuration = configuration;
        this.eventListenersInformation = eventListenersInformation;
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
    public void listen(String messageString) {
        System.out.println("recieved order: " + messageString);

        Map<String, Object> toMap = deserializeToMap(messageString);
        String eventName = valueOf(toMap.get("name"));
        DomainEvent domainEventToExecute = eventListenersInformation.getInstanceFor(eventName);
        DomainEvent domainEventToPublish = domainEventToExecute.fromPrimitives(getBody(toMap));

        this.eventBus.publish(domainEventToPublish);
    }

    private Map<String, Object> getBody(Map<String, Object> map){
        return (Map<String, Object>) map.get("body");
    }

    private Map<String, Object> deserializeToMap(String rawString){
        return new JSONObject(rawString).toMap();
    }
}