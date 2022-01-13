package es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.Getter;

public class SellOrder extends ExecutionOrder {
    @Getter private OrderPositionIdToSell positionIdToSell;

    public SellOrder(OrderId orderId, OrderClientId clientId, OrderDate date, OrderState state, OrderTicker ticker,
                     PendingOrderType pendingOrderType, OrderQuantity quantity, OrderPriceToExecute priceToExecute,
                     OrderPositionIdToSell positionIdToSell){

        super(orderId, clientId, date, pendingOrderType, state, ticker, quantity, priceToExecute);
        this.positionIdToSell = positionIdToSell;
    }

    public SellOrder() {
        super(null, null, null, null, null, null, null, null);
    }

    public OrderId getOrderId(){
        return super.orderId;
    }

    public SellOrder changeStateTo(OrderState orderState){
        return new SellOrder(orderId, clientId, date, orderState, ticker, pendingOrderType, quantity, priceToExecute, positionIdToSell);
    }

    public SellOrder updateQuantity(OrderQuantity orderQuantity){
        return new SellOrder(orderId, clientId, date, state, ticker, pendingOrderType, orderQuantity, priceToExecute, positionIdToSell);
    }

    public SellOrder updateExecutionPrice(OrderPriceToExecute priceToExecute){
        return new SellOrder(orderId, clientId, date, state, ticker, pendingOrderType, quantity, priceToExecute, positionIdToSell);
    }

    public static SellOrder create(String clientId, String ticker, int quantity, double executinPrice, String positionIdToSell){
        return new SellOrder(
                OrderId.generate(),
                OrderClientId.of(clientId),
                OrderDate.now(),
                OrderState.pending(),
                OrderTicker.of(ticker),
                PendingOrderType.sell(),
                OrderQuantity.of(quantity),
                OrderPriceToExecute.of(executinPrice),
                OrderPositionIdToSell.of(positionIdToSell)
        );
    }
}
