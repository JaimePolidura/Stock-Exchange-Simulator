package es.jaime.exchange.domain.services;

public interface ExchangeConfiguration {
    String getTicker();

    String errorOrdersExchangeName();
    String errorOrdersQueueName();
    String executedOrdersExchangeName();
    String executedOrdersQueueName();

    int matchingEngineSleep();
}
