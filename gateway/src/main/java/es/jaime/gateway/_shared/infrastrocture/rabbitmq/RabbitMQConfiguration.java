package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMQConfiguration {
    public static final String directExchangeName = "sxs.new-orders";
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
        List<Declarable> declarables = new ArrayList<>();

        DirectExchange directExchange = new DirectExchange(directExchangeName);
        declarables.add(directExchange);

        List<Queue> allQueuesForListedCompanies = repository.findAll().stream()
                .map(listedCompany -> listedCompany.ticker().value())
                .map(listedCompanyTicker -> directExchangeName + "." + listedCompanyTicker)
                .map(queueFullNmeString -> new Queue(queueFullNmeString, false))
                .toList();

        List<Binding> allBindings = allQueuesForListedCompanies.stream()
                .map(queue -> BindingBuilder
                        .bind(queue)
                        .to(directExchange)
                        .with(queue.getName()))
                .toList();

        declarables.addAll(allBindings);
        declarables.addAll(allQueuesForListedCompanies);

        return new Declarables(declarables);
    }
}
