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

        if(actualQuantity == quantitySold){
            changeStateToExecuted(orderSikd, event);
        }else{
            updateQuantity(orderSikd, actualQuantity - quantitySold);
            createNewOrder(orderSikd, event);
        }
    }

    private void changeStateToExecuted(SellOrder orderSell, SellOrderExecuted event){
        SellOrder orderStateChangedToExecuted = orderSell.changeStateTo(OrderState.executed())
                .updateExecutionPrice(OrderPriceToExecute.of(event.getExecutionPrice()));

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
                PendingOrderType.sell(),
                OrderState.executed(),
                orderSell.getTicker(),
                OrderQuantity.of(event.getQuantity()),
                OrderPriceToExecute.of(event.getExecutionPrice()),
                orderSell.getPositionIdToSell()
        ));
    }
}
