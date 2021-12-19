package es.jaime.exchange.infrastructure;

import com.rabbitmq.client.Channel;
import es.jaime.exchange.domain.EventBus;
import es.jaime.exchange.domain.ExchangeConfiguration;
import es.jaime.exchange.domain.Order;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn({"exchange-configuration"})
public class RabbitMQConfiguration{
    private final ExchangeConfiguration exchangeConfiguration;
    private final EventBus eventBus;

    public RabbitMQConfiguration(ExchangeConfiguration configuration, EventBus eventBus) {
        this.exchangeConfiguration = configuration;
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "${queue}")
    public void listener(Object body){
    }

    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("rabbitmq");
        factory.setPort(5672);

        return factory;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();

        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);

        return simpleMessageListenerContainer;
    }
}
