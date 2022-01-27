package es.jaime.exchange.domain.models.messages;

public enum MessageName {
    EXECUTED_BUY_ORDER("order-executed-buy"),
    ERROR_ORDER("order-error"),
    ORDER_CANCELLED("order-cancelled"),
    EXECUTED_SELL_ORDER("order-executed-sell"),
    EXCHANGE_STARTED("exchange-started"),
    NEW_ORDER_BUY("new-order-buy"),
    NEW_ORDER_CANCEL("cancel-order"),
    NEW_ORDER_SELL("new-order-sell");

    private final String name;

    MessageName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
