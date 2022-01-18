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
    private String ticker;

    @Override
    public void run(String[] args) {
        this.queueNewOrders = args[0];
        this.eventsExchange = args[1];
        this.mathcingEngineSleepTime = Integer.parseInt(args[2]);
        this.ticker = args[3];

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
}
