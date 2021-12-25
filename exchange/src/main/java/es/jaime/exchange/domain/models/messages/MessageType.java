package es.jaime.exchange.domain.models.messages;

public enum MessageType {
    EXECUTED_ORDER("ORDER.EXECUTED"),
    ERROR_ORDER("ORDER.ERROR");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
