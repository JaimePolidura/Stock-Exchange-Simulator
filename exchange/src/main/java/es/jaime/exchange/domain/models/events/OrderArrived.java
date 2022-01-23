package es.jaime.exchange.domain.models.events;

public abstract class OrderArrived extends AsyncDomainEvent{
    public abstract String getOrderId();
}
