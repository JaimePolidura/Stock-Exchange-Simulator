package es.jaime.exchange;

import es.jaime.exchange.application.MatchingEngineImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

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
        this.configuration.put("queue-exchange.name", "sxs.executed-orders");
        this.configuration.put("queue-queue.executedorders.names", format("sxs.executed-orders.%s", ticker));
    }

    public String getTicker(){
        return String.valueOf(this.configuration.get("ticker"));
    }

    public String getQueueExchangeName(){
        return String.valueOf(this.configuration.get("rabbitMQ-exchange.name"));
    }

    public String getQueueExecutedOrdersName(){
        return String.valueOf(this.configuration.get("rabbitMQ-queue.executedorders.names"));
    }
}
