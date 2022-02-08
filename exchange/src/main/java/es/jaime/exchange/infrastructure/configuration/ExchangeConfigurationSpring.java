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
@Order(1)
public class ExchangeConfigurationSpring implements CommandLineRunner, ExchangeConfiguration {
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
    public void run(String[] args) {
        this.queueNewOrders = args[0];
        this.eventsExchange = args[1];
        this.mathcingEngineSleepTime = Integer.parseInt(args[2]);
        this.ticker = args[3];
        this.initialDelay = Integer.parseInt(args[4]);
        this.eventsRoutingKey = args[5];
        this.exchangeName = args[6];
        this.redisHost = args[7];
        this.redisPort = Integer.parseInt(args[8]);
        this.redisPassword = args[9];

        System.out.println("ticker -> " + ticker);
    }

    @Override
    public String queueNewOrders() {
        return this.queueNewOrders;
    }

    @Override
    public String eventsExchangeName() {
        return this.eventsExchange;
    }

    @Override
    public int matchingEngineSleep() {
        return this.mathcingEngineSleepTime;
    }

    @Override
    public String ticker() {
        return this.ticker;
    }

    @Override
    public int initialDelay() {
        return this.initialDelay;
    }

    @Override
    public String eventsRoutingKey() {
        return this.eventsRoutingKey;
    }

    @Override
    public String exchangeName() {
        return this.exchangeName;
    }

    @Override
    public String redisHost() {
        return this.redisHost;
    }

    @Override
    public int redisPort() {
        return this.redisPort;
    }

    @Override
    public String redisPassword() {
        return this.redisPassword;
    }
}
