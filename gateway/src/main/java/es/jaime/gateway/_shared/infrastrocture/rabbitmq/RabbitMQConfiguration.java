package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String directExchangeName = "sxs.new-orders";
    public static final String newOrders = "sxs.new-orders";
    private final ListedCompaniesRepository repository;

    public RabbitMQConfiguration(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Declarables declarables() {
        DirectExchange directExchange = new DirectExchange(directExchangeName);

        return new Declarables(directExchange);
    }

//    @Bean
//    public Queue queue() {
//        return new Queue(newOrders, false);
//    }
//
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(directExchangeName);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("sxs.new-orders");
//    }
}
