package es.jaime.gateway._shared.domain;

import java.util.Arrays;

public enum EventName {
    EXECUTED_BUY_ORDER("order-executed-buy"),
    EXECUTED_SELL_ORDER("order-executed-sell"),
    ERROR_ORDER("order-error"),
    ORDER_CANCELLED("order-cancelled");

    private final String name;

    EventName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public static EventName fromEventName(String eventNameString){
        return Arrays.stream(EventName.values())
                .filter(eventname -> eventname.name.equalsIgnoreCase(eventNameString))
                .findFirst()
                .get();
    }
}
