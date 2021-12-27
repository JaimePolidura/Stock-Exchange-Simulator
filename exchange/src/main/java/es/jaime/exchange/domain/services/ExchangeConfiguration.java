package es.jaime.exchange.domain.services;

public interface ExchangeConfiguration {
    String getTicker();

    String errorOrdersExchangeName();
    String executedOrdersExchangeName();

    int matchingEngineSleep();
}
