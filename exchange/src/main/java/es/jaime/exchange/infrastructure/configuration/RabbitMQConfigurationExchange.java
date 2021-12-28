package es.jaime.exchange.infrastructure.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurationExchange {
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

