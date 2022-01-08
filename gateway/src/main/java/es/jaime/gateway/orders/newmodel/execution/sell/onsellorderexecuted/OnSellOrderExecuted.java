package es.jaime.gateway.orders.newmodel.execution.sell.onsellorderexecuted;

import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.orders._shared.domain.events.BuyOrderExecuted;
import es.jaime.gateway.orders.orders._shared.domain.events.OrderExecuted;
import es.jaime.gateway.orders.orders._shared.domain.events.SellOrderExecuted;
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
        OrderSell orderSikd = sellOrders.findByOrderId(OrderId.of(event.getOrderId()))
                .get();

        int actualQuantity = orderSikd.getQuantity().value();
        int quantitySold = event.getQuantity();

        if(actualQuantity == quantitySold){
            changeStateToExecuted(orderSikd);
        }else{
            updateQuantity(orderSikd, actualQuantity - quantitySold);
            createNewOrder(orderSikd, event);
        }
    }

    private void changeStateToExecuted(OrderSell orderSell){
        OrderSell orderBuyStateChangedToExecuted = orderSell.changeStateTo(OrderState.executed());

        this.sellOrders.save(orderBuyStateChangedToExecuted);
    }

    private void updateQuantity(OrderSell orderSell, int newQuantity){
        OrderSell orderBuyUpdatedQuantitty = orderSell.updateQuantity(OrderQuantity.of(newQuantity));

        this.sellOrders.save(orderBuyUpdatedQuantitty);
    }

    private void createNewOrder(OrderSell orderSell, OrderExecuted event){
        this.sellOrders.save(new OrderSell(
                OrderId.generate(),
                orderSell.getClientId(),
                OrderDate.of(event.getDate()),
                OrderType.buy(),
                OrderState.executed(),
                orderSell.getTicker(),
                OrderQuantity.of(event.getQuantity()),
                OrderExecutionPrice.of(event.getExecutionPrice()),
                orderSell.getPositionIdToSell()
        ));
    }
}
