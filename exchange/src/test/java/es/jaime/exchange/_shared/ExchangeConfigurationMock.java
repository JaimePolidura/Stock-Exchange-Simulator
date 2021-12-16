package es.jaime.exchange._shared;

import es.jaime.exchange.domain.ExchangeConfiguration;

public class ExchangeConfigurationMock implements ExchangeConfiguration {
    @Override
    public String getTicker() {
        return "AMZN";
    }

    @Override
    public String errorOrdersExchangeName() {
        return "not-needed";
    }

    @Override
    public String errorOrdersQueueName() {
        return "not-needed";
    }

    @Override
    public String executedOrdersExchangeName() {
        return "not-needed";
    }

    @Override
    public String executedOrdersQueueName() {
        return "not-needed";
    }

    @Override
    public int matchingEngineSleep() {
        return 1000;
    }
}
