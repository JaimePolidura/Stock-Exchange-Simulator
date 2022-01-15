package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

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

    public void changeStateToErrorAndSave(ExecutionOrder executionOrder){
        if(executionOrder.isBuy())
            saveToBuyOrders(executionOrder);
        else
            saveToSellOrders(executionOrder);
    }

    private void saveToBuyOrders(ExecutionOrder executionOrder){
        BuyOrder buyOrderWithError = ((BuyOrder) executionOrder).changeStateTo(OrderState.error());

        this.buyOrders.save(buyOrderWithError);
    }

    private void saveToSellOrders(ExecutionOrder executionOrder){
        SellOrder sellOrderUpdatedState = ((SellOrder) executionOrder).changeStateTo(OrderState.error());

        this.sellOrders.save(sellOrderUpdatedState);
    }
}
