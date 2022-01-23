package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.models.events.AsyncDomainEvent;
import es.jaime.exchange.domain.models.events.DomainEvent;
import es.jaime.exchange.domain.models.events.EventBus;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.infrastructure.ActiveOrderStorer;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RabbitListenerNewOrders implements CommandLineRunner {
    private static final String CONSUMER_NAME = "new-orders-consumer";

    private final ExchangeConfiguration configuration;
    private final DomainEventListenersInformation eventListenersInformation;
    private final RabbitListenerEndpointRegistry registry;
    private final EventBus eventBus;
    private final ActiveOrderStorer activeOrderStorer;

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
        String messageId = String.valueOf(toMap.get("id"));
        String eventName = valueOf(toMap.get("name"));

        if(activeOrderStorer.contains(messageId)){
            System.out.printf("Duplicated message for id %s", messageId);
            return;
        }

        AsyncDomainEvent domainEventToExecute = eventListenersInformation.getInstanceFor(eventName);
        DomainEvent domainEventToPublish = domainEventToExecute.fromPrimitives(toMap);

        this.eventBus.publish(domainEventToPublish);
    }


    private Map<String, Object> deserializeToMap(String rawString){
        return new JSONObject(rawString).toMap();
    }
}
