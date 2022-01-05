package es.jaime.gateway._shared.domain.messages;

public enum MessageNames {
    NEW_ORDER_BUY("new-order-buy"),
    ORDER_CANCEL("cancel-order"),
    NEW_ORDER_SELL("new-order-sell"),
    START_SERVICES("start-services");

    private final String name;

    MessageNames(String name) {
        this.name = name;
    }
}
