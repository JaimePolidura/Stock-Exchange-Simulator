package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExecutionOrderSaver {
    private final BuyOrderRepostory buyOrders;
    private final SellOrderRepostiry sellOrders;

    public void changeQuantityAndSave(ExecutionOrder executionOrder,  OrderQuantity newQuantity){
        if(executionOrder.isBuy()){
            changeQuantityAndSaveToBuyOrders(executionOrder, newQuantity);
        }else{
            changeQuantityAndSaveToSellOrders(executionOrder, newQuantity);
        }
    }

    private void changeQuantityAndSaveToBuyOrders(ExecutionOrder executionOrder, OrderQuantity quantity){
        BuyOrder buyOrderUpdated = ((BuyOrder) executionOrder).updateQuantity(quantity);

        this.buyOrders.save(buyOrderUpdated);
    }

    private void changeQuantityAndSaveToSellOrders(ExecutionOrder executionOrder, OrderQuantity quantity){
        SellOrder sellOrderUpdated = ((SellOrder) executionOrder).updateQuantity(quantity);

        this.sellOrders.save(sellOrderUpdated);
    }

    public void changeStateAndSave(ExecutionOrder executionOrder, OrderState orderStateToChange){
        if(executionOrder.isBuy())
            changeStateAndSaveToBuyOrders(executionOrder, orderStateToChange);
        else
            changeStateAndSaveToSellOrders(executionOrder, orderStateToChange);
    }

    private void changeStateAndSaveToBuyOrders(ExecutionOrder executionOrder, OrderState orderStateToChange){
        BuyOrder buyOrderWithError = ((BuyOrder) executionOrder).changeStateTo(orderStateToChange);

        this.buyOrders.save(buyOrderWithError);
    }

    private void changeStateAndSaveToSellOrders(ExecutionOrder executionOrder, OrderState orderStateToChange){
        SellOrder sellOrderUpdatedState = ((SellOrder) executionOrder).changeStateTo(orderStateToChange);

        this.sellOrders.save(sellOrderUpdatedState);
    }
}
