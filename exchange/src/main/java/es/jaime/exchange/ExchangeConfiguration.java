package es.jaime.exchange;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExchangeConfiguration implements ApplicationRunner {
    private final Map<String, Object> configuration;

    public ExchangeConfiguration() {
        this.configuration = new HashMap<>();
    }

    @Override
    public void run(ApplicationArguments args) {
        String ticker = args.getSourceArgs()[0];

        this.configuration.put("ticker", ticker);
        this.configuration.put("rabbitmq-executedOrders-exchange.name", "sxs.executed-orders");
        this.configuration.put("rabbitmq-executedOrders-queue.name", "sxs.executed-orders");

        this.configuration.put("rabbitmq-errorOrders-exchange.name", "sxs.error-orders");
        this.configuration.put("rabbitmq-errorOrders-queue.name", "sxs.error-orders");
        this.configuration.put("matchcingengine-sleep", 100);
    }

    public String getTicker(){
        return String.valueOf(this.configuration.get("ticker"));
    }

    public String errorOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-exchange.name"));
    }

    public String errorOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-queue.name"));
    }

    public String executedOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-exchange.name"));
    }

    public String executedOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-queue.name"));
    }

    public int matchingEngineSleep(){
        return (int) this.configuration.get("matchcingengine-sleep");
    }
}
