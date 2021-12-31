package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.Utils;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Configuration("rabbitmq-declarables")
@DependsOn({"rabbitmq-configuration"})
public class RabbitMQDeclarables {
    public static final String newOrders = "sxs.exchange.new-orders";
    public static final String start = "sxs.start";
    public static final String exchangeEvents = "sxs.exchange.events";

    private final ListedCompanyFinderService listedCompanyFinder;

    public RabbitMQDeclarables(ListedCompanyFinderService listedCompanyFinder) {
        this.listedCompanyFinder = listedCompanyFinder;
    }

    @Bean
    public Declarables declarables() {
        List<Declarable> start = start();
        List<Declarable> newOrders = newOrders();
        List<Declarable> exchangeEventsListeners = exchangeEventsListeners();

        return new Declarables(Utils.toList(start, newOrders, exchangeEventsListeners));
    }

    //TODO Improve with reflections
    private List<Declarable> exchangeEventsListeners(){
        List<Declarable> declarablesToReturn = new ArrayList<>();
        TopicExchange exchange = new TopicExchange(exchangeEvents);

        List<String> events = eventNames();
        List<String> subscribers = eventsListeners();

        for (String subscriber : subscribers) {
            for (String event : events) {
                String queueName = format("%s.%s.%s", exchangeEvents, subscriber, event);
                String bindingKey = format("%s.*.%s", exchangeEvents, event);

                Queue queue = new Queue(queueName);
                Binding binding = BindingBuilder
                        .bind(queue)
                        .to(exchange)
                        .with(bindingKey);

                declarablesToReturn.add(queue);
                declarablesToReturn.add(binding);
            }
        }
        declarablesToReturn.add(exchange);

        return declarablesToReturn;
    }

    private List<String> eventsListeners(){
        return List.of("error-order", "sell-order-executed", "buy-order-executed");
    }

    private List<String> eventNames() {
        return List.of("gateway", "client-order-event-dispatcher");
    }

    private List<Declarable> newOrders() {
        List<ListedCompany> listedCompanies = listedCompanyFinder.all();
        List<Declarable> declarablesToReturn = new ArrayList<>();

        TopicExchange newOrdersExchange = new TopicExchange(newOrders);

        for (ListedCompany listedCompanie : listedCompanies) {
            String queueName = format("%s.%s", newOrders, listedCompanie.ticker().value());

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
        TopicExchange startTopicExchange = new TopicExchange(start);
        Queue startQueue = new Queue(start, false);
        Binding binding = BindingBuilder.bind(startQueue).to(startTopicExchange).with(start);

        return List.of(startTopicExchange, startQueue, binding);
    }
}
