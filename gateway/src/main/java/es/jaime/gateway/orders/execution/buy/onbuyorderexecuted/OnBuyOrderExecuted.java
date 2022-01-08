package es.jaime.gateway.orders.execution.buy.onbuyorderexecuted;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution._shared.OrderExecuted;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderExecuted;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnBuyOrderExecuted {
    private final BuyOrderRepostiry buyOrders;

    public OnBuyOrderExecuted(BuyOrderRepostiry buyOrders) {
        this.buyOrders = buyOrders;
    }

        @EventListener({BuyOrderExecuted.class})
    public void on(BuyOrderExecuted event){
        OrderBuy orderBought = buyOrders.findByOrderId(OrderId.of(event.getOrderId()))
                .get();

        int actualQuantity = orderBought.getQuantity().value();
        int quantityBought = event.getQuantity();

        if(actualQuantity == quantityBought){
            changeStateToExecuted(orderBought);
        }else{
            updateQuantity(orderBought, actualQuantity - quantityBought);
            createNewOrder(orderBought, event);
        }
    }

    private void changeStateToExecuted(OrderBuy orderBuy){
        OrderBuy orderBuyStateChangedToExecuted = orderBuy.changeStateTo(OrderState.executed());

        this.buyOrders.save(orderBuyStateChangedToExecuted);
    }

    private void updateQuantity(OrderBuy orderBuy, int newQuantity){
        OrderBuy orderBuyUpdatedQuantitty = orderBuy.updateQuantity(OrderQuantity.of(newQuantity));

        this.buyOrders.save(orderBuyUpdatedQuantitty);
    }

    private void createNewOrder(OrderBuy orderBuy, OrderExecuted event){
        this.buyOrders.save(new OrderBuy(
                OrderId.generate(),
                orderBuy.getClientId(),
                OrderDate.of(event.getDate()),
                OrderType.buy(),
                OrderState.executed(),
                orderBuy.getTicker(),
                OrderQuantity.of(event.getQuantity()),
                OrderExecutionPrice.of(event.getExecutionPrice())
        ));
    }
}
