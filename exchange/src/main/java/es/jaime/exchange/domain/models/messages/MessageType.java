package es.jaime.exchange.domain.models.messages;

public enum MessageType {
    EXECUTED_BUY_ORDER("ORDER.BUY.EXECUTED"),
    EXECUTED_SELL_ORDER("ORDER.SELL.EXECUTED"),
    ERROR_ORDER("ORDER.ERROR");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
