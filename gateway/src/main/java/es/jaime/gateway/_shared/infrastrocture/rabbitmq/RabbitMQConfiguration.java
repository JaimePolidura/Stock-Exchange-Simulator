package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration("rabbitmq-configuration")
public class RabbitMQConfiguration {
    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("rabbitmq");
        factory.setPort(5672);

        return factory;
    }
}
