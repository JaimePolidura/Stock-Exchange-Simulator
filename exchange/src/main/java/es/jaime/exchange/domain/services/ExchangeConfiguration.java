package es.jaime.exchange.domain.services;

public interface ExchangeConfiguration {
    String queueNewOrders();

    String eventsExchangeName();

    int matchingEngineSleep();
}
