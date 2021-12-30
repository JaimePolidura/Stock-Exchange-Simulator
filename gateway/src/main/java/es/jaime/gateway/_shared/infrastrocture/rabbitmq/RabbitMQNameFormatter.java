package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.ordertypes.domain.OrderType;
import es.jaime.gateway.ordertypes.domain.OrderTypeName;

public final class RabbitMQNameFormatter {
    public static final String NEW_ORDERS = "sxs.new-orders";

    private RabbitMQNameFormatter () {}

    public static String newOrdersQueue(OrderType orderType, ListedCompany listedCompany){
        return String.format("%s.%s.%s", NEW_ORDERS, listedCompany.ticker().value(), orderType.getName().value());
    }

    public static String routingKeyNewOrders(OrderTypeName orderType, OrderTicker orderTicker){
        return String.format("%s.%s.%s", NEW_ORDERS, orderTicker.value(), orderType.value());
    }
}
