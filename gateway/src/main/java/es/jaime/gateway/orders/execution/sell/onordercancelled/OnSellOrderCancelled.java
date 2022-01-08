package es.jaime.gateway.orders.execution.sell.onordercancelled;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.cancel._shared.domain.OrderCancelled;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnSellOrderCancelled {
    private final SellOrderRepostiry sellOrders;

    public OnSellOrderCancelled(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled event){
        Optional<SellOrder> sellOrderOptional = sellOrders.findByOrderId(OrderId.of(event.getOrderIdCancelled()));

        if(sellOrderOptional.isPresent()){
            updateStateToCancelled(sellOrderOptional);
        }
    }

    private void updateStateToCancelled(Optional<SellOrder> sellOrderOptional) {
        SellOrder orderSellStateUpdated = sellOrderOptional.get()
                .changeStateTo(OrderState.cancelled());

        this.sellOrders.save(orderSellStateUpdated);
    }
}
