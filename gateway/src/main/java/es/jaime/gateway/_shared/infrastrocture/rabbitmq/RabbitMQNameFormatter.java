package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.infrastrocture.exchanges.ExchangesStarter;
import es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer.ExchangeStarted;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;

import java.util.List;

import static java.lang.String.*;

public final class RabbitMQNameFormatter {
    public static final String GATEWAY = "gateway";

    private static final String DOMAIN_NAME = "sxs";
    private static final String EVENTS = "events";
    private static final String START = "start";

    public static final String START_EXCHANGE = DOMAIN_NAME + "." + START; //sxs.start
    public static final String EVENTS_EXCHANGE = DOMAIN_NAME + "." + EVENTS; //sxs.events
    public static final String EVENTS_ROUTING_KEY = DOMAIN_NAME + "." + EVENTS + ".%s.*";

    private RabbitMQNameFormatter () {}

    public static String eventListenerQueueName(String listener, EventName eventName){
        return format("%s.events.%s.%s", DOMAIN_NAME, eventName.getName(), listener);
    }

    public static String eventListenerRoutingKey(EventName event){
        return format("%s.events.%s.*", DOMAIN_NAME, event.getName());
    }

    public static String newOrdersQueueName(String ticker){
        return eventListenerQueueName(ExchangesStarter.nameForExchangeContainer(ticker), EventName.ORDER_PUBLISHED);
    }
}
