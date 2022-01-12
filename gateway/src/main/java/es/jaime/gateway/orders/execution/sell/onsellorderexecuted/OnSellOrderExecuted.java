package es.jaime.gateway.orders.execution.sell.onsellorderexecuted;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution._shared.domain.OrderExecuted;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderExecuted;
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

        if(actualQuantity == quantitySold){
            changeStateToExecuted(orderSikd, event);
        }else{
            updateQuantity(orderSikd, actualQuantity - quantitySold);
            createNewOrder(orderSikd, event);
        }
    }

    private void changeStateToExecuted(SellOrder orderSell, SellOrderExecuted event){
        SellOrder orderStateChangedToExecuted = orderSell.changeStateTo(OrderState.executed())
                .updateExecutionPrice(OrderExecutionPrice.of(event.getExecutionPrice()));

        this.sellOrders.save(orderStateChangedToExecuted);
    }

    private void updateQuantity(SellOrder orderSell, int newQuantity){
        SellOrder orderUpdatedQuantitty = orderSell.updateQuantity(OrderQuantity.of(newQuantity));

        this.sellOrders.save(orderUpdatedQuantitty);
    }

    private void createNewOrder(SellOrder orderSell, OrderExecuted event){
        this.sellOrders.save(new SellOrder(
                OrderId.generate(),
                orderSell.getClientId(),
                OrderDate.of(event.getDate()),
                OrderType.sell(),
                OrderState.executed(),
                orderSell.getTicker(),
                OrderQuantity.of(event.getQuantity()),
                OrderExecutionPrice.of(event.getExecutionPrice()),
                orderSell.getPositionIdToSell()
        ));
    }
}
