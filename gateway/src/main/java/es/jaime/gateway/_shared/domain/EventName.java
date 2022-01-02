package es.jaime.gateway._shared.domain;

public enum EventName {
    EXECUTED_BUY_ORDER("order-executed-buy"),
    EXECUTED_SELL_ORDER("order-executed-sell"),
    ERROR_ORDER("order-error");

    private final String name;

    EventName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
