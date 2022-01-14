package es.jaime.gateway.orders.pendingorder.execution.sell.onsellorderexecuted;

import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderExecuted;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderExecuted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnSellOrderExecuted {
    private final SellOrderRepostiry sellOrders;

    public OnSellOrderExecuted(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @EventListener({SellOrderExecuted.class})
    public void on(SellOrderExecuted event){
        SellOrder orderSikd = sellOrders.findByOrderId(OrderId.of(event.getOrderId()))
                .get();

        int actualQuantity = orderSikd.getQuantity().value();
        int quantitySold = event.getQuantity();
        
        if(actualQuantity == quantitySold)
            deleteOrder(orderSikd);
        else
            updateQuantity(orderSikd, actualQuantity - quantitySold);
    }

    private void deleteOrder(SellOrder orderSell){
        this.sellOrders.deleteByOrderId(orderSell.getOrderId());
    }

    private void updateQuantity(SellOrder orderSell, int newQuantity){
        SellOrder orderUpdatedQuantitty = orderSell.updateQuantity(OrderQuantity.of(newQuantity));

        this.sellOrders.save(orderUpdatedQuantitty);
    }
}
