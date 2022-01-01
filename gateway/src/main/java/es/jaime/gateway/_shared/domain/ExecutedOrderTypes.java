package es.jaime.gateway._shared.domain;

public enum ExecutedOrderTypes {
    EXECUTED_BUY_ORDER("order-executed-buy"),
    EXECUTED_SELL_ORDER("order-executed-sell"),
    ERROR_ORDER("order-error");

    private final String type;

    ExecutedOrderTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
