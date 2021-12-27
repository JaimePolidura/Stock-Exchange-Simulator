package es.jaime.exchange.infrastructure.configuration;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeConfigurationSpring implements ApplicationRunner, ExchangeConfiguration {
    private String ticker;

    @Override
    public void run(ApplicationArguments args) {
        this.ticker = args.getSourceArgs()[0];

        System.out.println("ticker -> " + ticker);
    }

    @Override
    public String getTicker(){
        return this.ticker;
    }

    @Override
    public String executedOrdersExchangeName(){
        return "sxs.executed-orders";
    }

    @Override
    public String errorOrdersExchangeName(){
        return "sxs.error-orders";
    }

    @Override
    public int matchingEngineSleep(){
        return 100;
    }
}
