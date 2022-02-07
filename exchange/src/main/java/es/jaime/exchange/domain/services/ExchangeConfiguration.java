package es.jaime.exchange.domain.services;

public interface ExchangeConfiguration {
    String queueNewOrders();
    String eventsExchangeName();
    int matchingEngineSleep();
    String ticker();
    int initialDelay();
    String eventsRoutingKey();
    String exchangeName();
}
