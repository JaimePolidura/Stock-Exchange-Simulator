package es.jaime.gateway._shared.domain;

public enum ExecutedOrderTypes {
    EXECUTED_BUY_ORDER("ORDER.BUY.EXECUTED"),
    EXECUTED_SELL_ORDER("ORDER.SELL.EXECUTED"),
    ERROR_ORDER("ORDER.ERROR");

    private final String type;

    ExecutedOrderTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
