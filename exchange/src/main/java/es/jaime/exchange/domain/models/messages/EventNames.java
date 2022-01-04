package es.jaime.exchange.domain.models.messages;

public enum EventNames {
    ORDER_EXECUTED_BUY("order-executed-buy"),
    ORDER_EXECUTED_SELL("order-executed-sell"),
    ORDER_ERROR("order-error"),
    ORDER_CANCELLED("order-cancelled");

    private final String name;
    
    EventNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
