package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("rabbitmq-configuration")
public class RabbitMQConfiguration {
    public static final String newOrders = "sxs.new-orders";
    public static final String executedOrders = "sxs.executed-orders";
    public static final String errorOrders = "sxs.error-orders";
    public static final String start = "sxs.start";

    private final ListedCompaniesRepository repository;

    public RabbitMQConfiguration(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("rabbitmq");
        factory.setPort(5672);

        return factory;
    }

    @Bean
    public Declarables declarables() {
        List<TopicExchange> allExchanges = new ArrayList<>();
        List<Queue> allQueues = new ArrayList<>();
        List<Binding> allBindings = new ArrayList<>();

        //START used for start the other queues
        TopicExchange startTopicExchange = new TopicExchange(start);
        allExchanges.add(startTopicExchange);
        Queue startQueue = new Queue(start, false);
        allQueues.add(startQueue);
        allBindings.add(BindingBuilder.bind(startQueue).to(startTopicExchange).with(start));

        //EXECUTED ORDERS
        TopicExchange executedOrdersExchange = new TopicExchange(executedOrders, true, false);
        allExchanges.add(executedOrdersExchange);

        Queue executedOrdersEventDispatcherQueue = new Queue( "sxs.executed-orders.client-order-event-dispatcher", false);
        allQueues.add(executedOrdersEventDispatcherQueue);
        allBindings.add(BindingBuilder.bind(executedOrdersEventDispatcherQueue)
                .to(executedOrdersExchange)
                .with("sxs.executed-orders.*"));

        Queue executedOrdersGatewayQueue = new Queue("sxs.executed-orders.gateway", false);
        allQueues.add(executedOrdersGatewayQueue);
        allBindings.add(BindingBuilder.bind(executedOrdersGatewayQueue)
                .to(executedOrdersExchange)
                .with("sxs.executed-orders.*"));

        //ERROR ORDDERS
        TopicExchange errorOrdersExchange = new TopicExchange(errorOrders, true, false);
        allExchanges.add(errorOrdersExchange);

        Queue errorOrdersEventDispatcherQueue = new Queue("sxs.error-orders.client-order-event-dispatcher");
        allQueues.add(errorOrdersEventDispatcherQueue);
        allBindings.add(BindingBuilder.bind(errorOrdersEventDispatcherQueue)
                .to(errorOrdersExchange)
                .with("sxs.error-orders.*"));

        Queue errorOrdersGatewayQueue = new Queue("sxs.error-orders.gateway");
        allQueues.add(errorOrdersGatewayQueue);
        allBindings.add(BindingBuilder.bind(errorOrdersGatewayQueue)
                .to(errorOrdersExchange)
                .with("sxs.error-orders.*"));

        // NEW ORDERS
        TopicExchange newOrdersExchange = new TopicExchange(newOrders);
        allExchanges.add(newOrdersExchange);

        List<Queue> allQueuesForListedCompanies = repository.findAll().stream()
                .map(listedCompany -> listedCompany.ticker().value())
                .map(listedCompanyTicker -> newOrders + "." + listedCompanyTicker)
                .map(queueFullNmeString -> new Queue(queueFullNmeString, false))
                .collect(Collectors.toList());
        allQueues.addAll(allQueuesForListedCompanies);

        allQueuesForListedCompanies.stream()
                .map(queue -> BindingBuilder
                        .bind(queue)
                        .to(newOrdersExchange)
                        .with(queue.getName()))
                .forEach(allBindings::add);

        List<Declarable> declarables = new ArrayList<>();
        declarables.addAll(allBindings);
        declarables.addAll(allQueues);
        declarables.addAll(allExchanges);

        return new Declarables(declarables);
    }
}
