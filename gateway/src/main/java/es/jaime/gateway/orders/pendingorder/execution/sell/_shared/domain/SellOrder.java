package es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.Getter;

public class SellOrder extends ExecutionOrder {
    @Getter private OrderPositionIdToSell positionIdToSell;

    public SellOrder(OrderId orderId, OrderClientId clientId, OrderDate date, PendingOrderType type, OrderState state, OrderTicker ticker,
                     OrderQuantity quantity, OrderPriceToExecute executionPrice, OrderPositionIdToSell positionIdToSell) {
        super(orderId, clientId, date, type, state, ticker, quantity, executionPrice);
        this.positionIdToSell = positionIdToSell;
    }

    public SellOrder() {
        super(null, null, null, null, null, null, null, null);
    }

    public OrderId getOrderId(){
        return super.orderId;
    }

    public SellOrder changeStateTo(OrderState orderState){
        return new SellOrder(orderId, clientId, date, type, orderState, ticker, quantity, priceToExecute, positionIdToSell);
    }

    public SellOrder updateQuantity(OrderQuantity orderQuantity){
        return new SellOrder(orderId, clientId, date, type, state, ticker, orderQuantity, priceToExecute, positionIdToSell);
    }

    public SellOrder updateExecutionPrice(OrderPriceToExecute executionPrice){
        return new SellOrder(orderId, clientId, date, type, state, ticker, quantity, executionPrice, positionIdToSell);
    }

    public static SellOrder create(String clientId, String ticker, int quantity, double executinPrice, String positionIdToSell){
        return new SellOrder(OrderId.generate(), OrderClientId.of(clientId), OrderDate.now(), PendingOrderType.buy(), OrderState.pending(),
                OrderTicker.of(ticker), OrderQuantity.of(quantity), OrderPriceToExecute.of(executinPrice), OrderPositionIdToSell.of(positionIdToSell));
    }
}
