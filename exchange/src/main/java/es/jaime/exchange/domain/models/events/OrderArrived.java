package es.jaime.exchange.domain.models.events;

public abstract class OrderArrived extends DomainEvent{
    public abstract String getOrderId();
}
