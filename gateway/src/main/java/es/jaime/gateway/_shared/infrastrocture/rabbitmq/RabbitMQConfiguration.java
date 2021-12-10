package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitMQConfiguration {
    public static final String directExchangeName = "sxs.new-orders";
    private final ListedCompaniesRepository repository;

    public RabbitMQConfiguration(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Declarables declarables() {
        List<Declarable> declarables = new ArrayList<>();

        DirectExchange directExchange = new DirectExchange(directExchangeName);
        declarables.add(directExchange);

        List<Queue> allQueuesForListedCompanies = repository.findAll().stream()
                .map(listedCompany -> listedCompany.ticker().value())
                .map(listedCompabyTicker -> directExchangeName + "." + listedCompabyTicker)
                .map(queueFullNmeString -> new Queue(queueFullNmeString, false))
                .collect(Collectors.toList());

        List<Binding> allBindings = allQueuesForListedCompanies.stream()
                .map(queue -> BindingBuilder
                        .bind(queue)
                        .to(directExchange)
                        .with(queue.getName()))
                .collect(Collectors.toList());

        declarables.addAll(allBindings);
        declarables.addAll(allQueuesForListedCompanies);

        return new Declarables(declarables);
    }
}
