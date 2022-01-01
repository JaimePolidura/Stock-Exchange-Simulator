package es.jaime.gateway._shared.infrastrocture.rabbitmq.rabbitlistener;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway._shared.domain.event.EventBus;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@DependsOn({"rabbitmq-starter"})
public class RabbitMQEventsListener implements CommandLineRunner {
    private static final String CONSUMER_NAME = "domain-event-consumer";

    private final DomainEventListenersInformation eventListenersInformation;
    private final RabbitListenerEndpointRegistry registry;
    private final EventBus eventBus;

    public RabbitMQEventsListener(DomainEventListenersInformation eventListenersInformation, RabbitListenerEndpointRegistry registry,
                                  EventBus eventBus) {
        this.eventListenersInformation = eventListenersInformation;
        this.registry = registry;
        this.eventBus = eventBus;
    }


    @Override
    public void run(String... args) throws Exception {
        AbstractMessageListenerContainer container = (AbstractMessageListenerContainer) registry.getListenerContainer(CONSUMER_NAME);

        container.addQueueNames(getQueuesNames());

        container.start();
    }

    @RabbitListener(id = CONSUMER_NAME, autoStartup = "false")
    public void consumer(String messageString) {
        Map<String, Object> toMap = deserializeToMap(messageString);
        String eventName = String.valueOf(toMap.get("name"));
        DomainEvent domainEventToExecute = eventListenersInformation.getInstanceFor(eventName);
        DomainEvent domainEventToPublsh = domainEventToExecute.fromPrimitives(toMap);

        this.eventBus.publish(domainEventToPublsh);
    }

    private Map<String, Object> deserializeToMap(String rawString){
        return new JSONObject(rawString).toMap();
    }

    //TODO Improve with reflections
    private String[] getQueuesNames(){
        return new String[] {
                "sxs.exchange.events.gateway.order-error",
                "sxs.exchange.events.gateway.order-executed-buy",
                "sxs.exchange.events.gateway.order-executed-sell"
        };
    }
}
