package es.jaime.exchange.infrastructure.configuration;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component("exchange-configuration")
public class ExchangeConfigurationSpring implements ExchangeConfiguration {
    private String queueNewOrders;
    private String eventsExchange;
    private int mathcingEngineSleepTime;
    private int initialDelay;
    private String ticker;
    private String eventsRoutingKey;
    private String exchangeName;
    private String redisHost;
    private int redisPort;
    private String redisPassword;

    @Override
    public String queueNewOrders() {
        return System.getenv("NEW_ORDERS_QUEUE_NAME");
    }

    @Override
    public String eventsExchangeName() {
        return System.getenv("EVENTS_EXCHANGE");
    }

    @Override
    public int matchingEngineSleep() {
        return Integer.parseInt(System.getenv("EXCHANGE_DELAY_BETWEEN_CHECK"));
    }

    @Override
    public String ticker() {
        return System.getenv("TICKER");
    }

    @Override
    public int initialDelay() {
        return Integer.parseInt(System.getenv("EXCHANGE_INITIAL_DELAY"));
    }

    @Override
    public String eventsRoutingKey() {
        return System.getenv("EVENTS_ROUTING_KEY");
    }

    @Override
    public String exchangeName() {
        return System.getenv("EXCHANGE_NAME");
    }

    @Override
    public String redisHost() {
        return System.getenv("REDIS_HOST");
    }

    @Override
    public int redisPort() {
        return Integer.parseInt(System.getenv("REDIS_PORT"));
    }

    @Override
    public String redisPassword() {
        return System.getenv("REDIS_PASSWORD");
    }
}
