package es.jaimetruman.exchanges.orderexecutor;

import es.jaime.Event;
import es.jaimetruman._shared.Order;
import es.jaimetruman.exchangedistribuitor._shared.OrderType;
import lombok.Getter;

public final class OrderExecutedEvent extends Event {
    @Getter private final Order buyer;
    @Getter private final Order seller;

    //In Exchange.class there is no way to distinguish them
    public OrderExecutedEvent(Order orderA, Order orderB) {
        if(orderA.getOrderType() == OrderType.BUY){
            this.buyer = orderA;
            this.seller = orderB;
        }else{
            this.buyer = orderB;
            this.seller = orderA;
        }
    }

    @Override
    public String getEventName() {
        return "exchange.eventexecuted";
    }
}
