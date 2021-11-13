package es.jaimetruman._shared;

import es.jaimetruman.exchangedistribuitor._shared.OrderExecutionType;
import es.jaimetruman.exchangedistribuitor._shared.OrderType;
import es.jaimetruman.exchangedistribuitor._shared.RawOrder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Order {
    @Getter private final UUID orderId;
    @Getter private final LocalDateTime time;
    @Getter private final UUID clientId;
    @Getter private final String ticker;
    @Getter private final int quantity;
    @Getter private final OrderType orderType;
    @Getter private final OrderExecutionType orderExecutionType;

    public Order(RawOrder rawOrder){
        this.orderId = UUID.randomUUID();
        this.clientId = rawOrder.getClientId();
        this.time = rawOrder.getTime();
        this.ticker = rawOrder.getTicker();
        this.quantity = rawOrder.getQuantity();
        this.orderType = rawOrder.getOrderType();
        this.orderExecutionType = rawOrder.getOrderExecutionType();
    }
}
