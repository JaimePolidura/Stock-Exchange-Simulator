package es.jaime.gateway._shared.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.jaime.gateway._shared.domain.EventName.Listener.*;

public enum EventName {
    EXECUTED_BUY_ORDER("order-executed-buy", List.of(CLIENT_ORDER_EVENT_DISPATCHER, GATEWAY)),
    EXECUTED_SELL_ORDER("order-executed-sell", List.of(CLIENT_ORDER_EVENT_DISPATCHER, GATEWAY)),
    ERROR_ORDER("order-error", List.of(CLIENT_ORDER_EVENT_DISPATCHER, GATEWAY)),
    ORDER_CANCELLED("order-cancelled", List.of(CLIENT_ORDER_EVENT_DISPATCHER, GATEWAY)),
    EXCHANGE_STARTED("exchange-started", List.of(GATEWAY));

    private final String name;
    private final List<Listener> listeners;

    EventName(String name, List<Listener> listeners) {
        this.name = name;
        this.listeners = listeners;
    }
    
    public String getName() {
        return name;
    }

    public List<String> getListeners(){
        return listeners.stream()
                .map(l -> l.nameListener)
                .collect(Collectors.toList());
    }

    public static EventName fromEventName(String eventNameString){
        return Arrays.stream(EventName.values())
                .filter(eventname -> eventname.name.equalsIgnoreCase(eventNameString))
                .findFirst()
                .get();
    }

    public enum Listener {
        GATEWAY("gateway"), CLIENT_ORDER_EVENT_DISPATCHER("client-order-event-dispatcher");

        private final String nameListener;

        Listener(String nameListener) {
            this.nameListener = nameListener;
        }
    }
}
