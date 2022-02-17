package es.jaime.gateway._shared.infrastrocture.exchanges;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.exchange.ExchangeDescriptor;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ExchangeDescriptorImpl implements ExchangeDescriptor {
    private final ApplicationConfiguration configuration;

    @Override
    public String name(String ticker) {
        return format("exchange-%s-%s", ticker.toLowerCase(), firstPartOfUUID());
    }

    private static String firstPartOfUUID(){
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public Map<String, Object> properties(String ticker, String exchangeName) {
        return Map.of(
                "NEW_ORDERS_QUEUE_NAME", RabbitMQNameFormatter.newOrdersQueueName(ticker),
                "EVENTS_EXCHANGE", RabbitMQNameFormatter.EVENTS_EXCHANGE,
                "EXCHANGE_DELAY_BETWEEN_CHECK", configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                "TICKER", ticker,
                "EXCHANGE_INITIAL_DELAY", configuration.get("EXCHANGE_INITIAL_DELAY"),
                "EVENTS_ROUTING_KEY", RabbitMQNameFormatter.EVENTS_ROUTING_KEY,
                "EXCHANGE_NAME", exchangeName,
                "REDIS_HOST", configuration.get("REDIS_HOST"),
                "REDIS_PORT", configuration.get("REDIS_PORT"),
                "REDIS_PASSWORD", configuration.get("REDIS_PASSWORD")
        );
    }
}
