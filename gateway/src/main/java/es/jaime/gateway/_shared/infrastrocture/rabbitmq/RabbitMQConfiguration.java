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

    private final ListedCompaniesRepository repository;

    public RabbitMQConfiguration(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);

        return factory;
    }

    @Bean
    public Declarables declarables() {
        List<Declarable> declarables = new ArrayList<>();

        List<DirectExchange> allExchanges = new ArrayList<>();
        List<Queue> allQueues = new ArrayList<>();
        List<Binding> allBindings = new ArrayList<>();

        //EXECUTED ORDERS
        DirectExchange executedOrdersExchange = new DirectExchange(executedOrders);
        allExchanges.add(executedOrdersExchange);
        Queue executedOrdersQueue = new Queue(executedOrders, false);
        allQueues.add(executedOrdersQueue);
        allBindings.add(BindingBuilder.bind(executedOrdersQueue).to(executedOrdersExchange).with(executedOrders));

        //ERROR ORDDERS
        DirectExchange errorOrdersExchange = new DirectExchange(errorOrders);
        allExchanges.add(errorOrdersExchange);
        Queue errorOrdersQueue = new Queue(errorOrders, false);
        allQueues.add(errorOrdersQueue);
        allBindings.add(BindingBuilder.bind(errorOrdersQueue).to(errorOrdersExchange).with(errorOrders));

        // NEW ORDERS
        DirectExchange newOrdersExchange = new DirectExchange(newOrders);
        declarables.add(newOrdersExchange);

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

        declarables.addAll(allBindings);
        declarables.addAll(allQueues);
        declarables.addAll(allExchanges);

        return new Declarables(declarables);
    }
}
