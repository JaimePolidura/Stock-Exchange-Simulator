package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.Utils;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Configuration("rabbitmq-declarables")
@DependsOn({"rabbitmq-configuration"})
@AllArgsConstructor
public class RabbitMQDeclarables {
    private final ListedCompanyFinderService listedCompanyFinder;

    @Bean
    public Declarables declarables() {
        List<Declarable> start = start();
        List<Declarable> newOrders = newOrders();
        List<Declarable> exchangeEventsListeners = exchangeEventsListeners();

        return new Declarables(Utils.toList(start, newOrders, exchangeEventsListeners));
    }

    private List<Declarable> exchangeEventsListeners(){
        List<Declarable> declarablesToReturn = new ArrayList<>();
        TopicExchange exchange = new TopicExchange(EVENTS_EXCHANGE);

        List<EventName> events = eventNames();

        for (EventName event : events) {
            List<String> listeners = event.getListeners();

            for (String listener : listeners) {
                String queueName = eventListenerQueueName(listener, event);
                String routingKey = eventListenerRoutingKey(event);

                Queue queue = new Queue(queueName);
                Binding binding = BindingBuilder
                        .bind(queue)
                        .to(exchange)
                        .with(routingKey);

                declarablesToReturn.add(queue);
                declarablesToReturn.add(binding);
            }
        }

        declarablesToReturn.add(exchange);

        return declarablesToReturn;
    }

    private List<EventName> eventNames() {
        return Arrays.stream(EventName.values())
                .collect(Collectors.toList());
    }

    private List<Declarable> newOrders() {
        List<ListedCompany> listedCompanies = listedCompanyFinder.all();
        List<Declarable> declarablesToReturn = new ArrayList<>();

        TopicExchange newOrdersExchange = new TopicExchange(NEW_ORDERS_EXCHNAGE);

        for (ListedCompany listedCompany : listedCompanies) {
            String queueName = newOrdersQueueName(listedCompany.ticker());

            Queue queue = new Queue(queueName);
            Binding binding = BindingBuilder.bind(queue)
                    .to(newOrdersExchange)
                    .with(queueName);

            declarablesToReturn.add(queue);
            declarablesToReturn.add(binding);
        }
        declarablesToReturn.add(newOrdersExchange);

        return declarablesToReturn;
    }

    private List<Declarable> start(){
        TopicExchange startTopicExchange = new TopicExchange(START_EXCHANGE);
        Queue startQueue = new Queue(START_EXCHANGE, false);
        Binding binding = BindingBuilder.bind(startQueue).to(startTopicExchange).with(START_EXCHANGE);

        return List.of(startTopicExchange, startQueue, binding);
    }
}
