package es.jaime.exchange.domain.models.messages;

public enum MessageNames {
    EXECUTED_BUY_ORDER("order-executed-buy"),
    ERROR_ORDER("order-error"),
    ORDER_CANCELLED("order-cancelled"),
    EXECUTED_SELL_ORDER("order-executed-sell");

    private final String name;

    MessageNames(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
