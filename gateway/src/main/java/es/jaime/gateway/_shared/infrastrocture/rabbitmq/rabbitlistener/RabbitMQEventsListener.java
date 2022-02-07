package es.jaime.gateway._shared.infrastrocture.rabbitmq.rabbitlistener;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway._shared.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;
import static java.lang.String.*;

@Component
@AllArgsConstructor
public class RabbitMQEventsListener implements CommandLineRunner {
    //TODO Improve using domaineventlistener information
    private final List<String> eventsToListen = List.of(
            "order-executed-buy", "order-executed-sell", "order-error", "order-cancelled", "exchange-started"
    );

    private static final String CONSUMER_NAME = "domain-event-consumer";

    private final DomainEventListenersInformation eventListenersInformation;
    private final RabbitListenerEndpointRegistry registry;
    private final EventBus eventBus;

    @Override
    public void run(String... args) {
        AbstractMessageListenerContainer container = (AbstractMessageListenerContainer) registry.getListenerContainer(CONSUMER_NAME);

        container.addQueueNames(getQueuesNames());

        container.start();
    }

    @RabbitListener(id = CONSUMER_NAME, autoStartup = "false")
    public void consumer(String messageString) {
        Map<String, Object> toMap = deserializeToMap(messageString);

        System.out.println("recieved event " + messageString);

        String eventName = valueOf(toMap.get("name"));
        DomainEvent domainEventToExecute = eventListenersInformation.getInstanceFor(EventName.fromEventName(eventName));
        DomainEvent domainEventToPublsh = domainEventToExecute.fromPrimitives(toMap);

        this.eventBus.publish(domainEventToPublsh);
    }

    private Map<String, Object> deserializeToMap(String rawString){
        return new JSONObject(rawString).toMap();
    }

    private String[] getQueuesNames(){
        return this.eventsToListen.stream()
                .map(event -> eventListenerQueueName(GATEWAY, EventName.fromEventName(event)))
                .toArray(String[]::new);
    }
}
