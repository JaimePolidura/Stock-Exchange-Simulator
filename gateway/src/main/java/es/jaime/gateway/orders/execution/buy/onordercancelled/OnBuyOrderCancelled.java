package es.jaime.gateway.orders.execution.buy.onordercancelled;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.cancel._shared.domain.OrderCancelled;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnBuyOrderCancelled {
    private final BuyOrderRepostory buyOrders;

    public OnBuyOrderCancelled(BuyOrderRepostory buyOrders) {
        this.buyOrders = buyOrders;
    }

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled event){
        Optional<BuyOrder> buyOrderOptional = buyOrders.findByOrderId(OrderId.of(event.getOrderIdCancelled()));

        if(buyOrderOptional.isPresent()){
            updateStateToCancelled(buyOrderOptional);
        }
    }

    private void updateStateToCancelled(Optional<BuyOrder> buyOrderOptional) {
        BuyOrder orderBuyStateUpdated = buyOrderOptional.get()
                .changeStateTo(OrderState.cancelled());

        this.buyOrders.save(orderBuyStateUpdated);
    }
}
