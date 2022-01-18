package es.jaime.exchange._shared;

import es.jaime.exchange.domain.services.ExchangeConfiguration;

public class ExchangeConfigurationMock implements ExchangeConfiguration {
    @Override
    public String queueNewOrders() {
        return "not needed";
    }

    @Override
    public String eventsExchangeName() {
        return "not needed";
    }

    @Override
    public int matchingEngineSleep() {
        return 250;
    }

    @Override
    public String ticker() {
        return "not needed";
    }
}
