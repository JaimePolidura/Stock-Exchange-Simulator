package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rabbitmq-configuration")
@AllArgsConstructor
public class RabbitMQConfiguration {
    private final ApplicationConfiguration gatewayConfiguration;
    private final ApplicationConfiguration configuration;

    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost(configuration.get("RABBIT_HOST"));
        factory.setPort(gatewayConfiguration.getInt("RABBIT_PORT"));

        return factory;
    }
}
