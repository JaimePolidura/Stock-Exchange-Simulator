package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;

import java.util.List;

import static java.lang.String.*;

public final class RabbitMQNameFormatter {
    public static final String EXCHANGE = "exchange";
    public static final String GATEWAY = "gateway";

    private static final String DOMAIN_NAME = "sxs";
    private static final String EVENTS = "events";
    private static final String START = "start";
    private static final String NEW_ORDERS = "new-orders";

    public static final String NEW_ORDERS_EXCHNAGE = DOMAIN_NAME + "." + EXCHANGE + "."  + NEW_ORDERS; //sxs.exchange.new-orders
    public static final String START_EXCHANGE = DOMAIN_NAME + "." + START; //sxs.start
    public static final String EVENTS_EXCHANGE = DOMAIN_NAME + "." + EXCHANGE + "." + EVENTS; //sxs.exchange.events

    private RabbitMQNameFormatter () {}

    public static String eventListenerQueueName(String listener, EventName eventName){
        return format("%s.%s.%s", EVENTS_EXCHANGE, listener, eventName.getName());
    }

    public static String eventListenerRoutingKey(EventName eventName){
        return format("%s.*.%s", EVENTS_EXCHANGE, eventName.getName());
    }

    public static String newOrdersQueueName(ListedCompanyTicker ticker){
        return format("%s.%s", NEW_ORDERS_EXCHNAGE, ticker.value());
    }

    public static List<String> getModuleListeners(){
        return List.of("gateway", "client-order-event-dispatcher");
    }
}
