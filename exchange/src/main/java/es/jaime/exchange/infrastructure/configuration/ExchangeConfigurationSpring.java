package es.jaime.exchange.infrastructure.configuration;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeConfigurationSpring implements ApplicationRunner, ExchangeConfiguration {
    private final Map<String, String> configuration;

    public ExchangeConfigurationSpring() {
        this.configuration = new HashMap<>();
    }

    @Override
    public void run(ApplicationArguments args) {
        String ticker = args.getSourceArgs()[0];

        System.out.println("ticker -> " + ticker);

        this.configuration.put("ticker", ticker);
        this.configuration.put("rabbitmq-executedOrders-exchange.name", "sxs.executed-orders");
        this.configuration.put("rabbitmq-executedOrders-queue.name", "sxs.executed-orders");

        this.configuration.put("rabbitmq-errorOrders-exchange.name", "sxs.error-orders");
        this.configuration.put("rabbitmq-errorOrders-queue.name", "sxs.error-orders");
        this.configuration.put("matchcingengine-sleep", "100");
    }

    @Override
    public String getTicker(){
        return String.valueOf(this.configuration.get("ticker"));
    }

    @Override
    public String errorOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-exchange.name"));
    }

    @Override
    public String errorOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-queue.name"));
    }

    @Override
    public String executedOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-exchange.name"));
    }

    @Override
    public String executedOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-queue.name"));
    }

    @Override
    public int matchingEngineSleep(){
        return Integer.parseInt(this.configuration.get("matchcingengine-sleep"));
    }
}
