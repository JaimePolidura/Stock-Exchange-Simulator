package es.jaime.exchange.infrastructure.configuration;

import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.OrderArrivedEvent;
import es.jaime.exchange.domain.models.Order;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurationExchange {
    private final EventBus eventBus;

    public RabbitMQConfigurationExchange(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Value("${queue}")
    private String queue;

    @SneakyThrows
    @RabbitListener(queues = "${queue}")
    public void listener(String incomeOrder){
        System.out.println("Order received " + incomeOrder);

        JSONObject json = new JSONObject(incomeOrder);

        this.eventBus.publish(new OrderArrivedEvent(Order.create(json)));
    }

    @Bean("exchange-rabbitmq-connection")
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("rabbitmq");
        factory.setPort(5672);

        return factory;
    }

    @Bean("exchange-rabbitmq-messagelistenercontainer")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();

        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);

        return simpleMessageListenerContainer;
    }
}

