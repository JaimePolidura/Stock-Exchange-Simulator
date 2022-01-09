package es.jaime.gateway.orders.execution.buy.onbuyorderexecuted;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution._shared.domain.OrderExecuted;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderExecuted;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnBuyOrderExecuted {
    private final BuyOrderRepostory buyOrders;

    public OnBuyOrderExecuted(BuyOrderRepostory buyOrders) {
        this.buyOrders = buyOrders;
    }

    @EventListener({BuyOrderExecuted.class})
    public void on(BuyOrderExecuted event){
        System.out.println(event.getOrderId());

        BuyOrder orderBought = buyOrders.findByOrderId(OrderId.of(event.getOrderId()))
                .get();

        int actualQuantity = orderBought.getQuantity().value();
        int quantityBought = event.getQuantity();

        if(actualQuantity == quantityBought){
            changeStateToExecuted(orderBought, event);
        }else{
            updateQuantity(orderBought, actualQuantity - quantityBought);
            createNewOrder(orderBought, event);
        }
    }

    private void changeStateToExecuted(BuyOrder orderBuy, BuyOrderExecuted event){
        BuyOrder orderBuyStateChangedToExecuted = orderBuy.changeStateTo(OrderState.executed())
                .updateExecutionPrice(OrderExecutionPrice.of(event.getExecutionPrice()));

        this.buyOrders.save(orderBuyStateChangedToExecuted);
    }

    private void updateQuantity(BuyOrder orderBuy, int newQuantity){
        BuyOrder orderBuyUpdatedQuantitty = orderBuy.updateQuantity(OrderQuantity.of(newQuantity));

        this.buyOrders.save(orderBuyUpdatedQuantitty);
    }

    private void createNewOrder(BuyOrder orderBuy, OrderExecuted event){
        this.buyOrders.save(new BuyOrder(
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
