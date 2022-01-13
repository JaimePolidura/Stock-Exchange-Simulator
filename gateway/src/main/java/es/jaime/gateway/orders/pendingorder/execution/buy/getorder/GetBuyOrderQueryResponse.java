package es.jaime.gateway.orders.pendingorder.execution.buy.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetBuyOrderQueryResponse implements QueryResponse {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final String state;
    @Getter private final String ticker;
    @Getter private final int quantity;
    @Getter private final double executionPrice;

    public static GetBuyOrderQueryResponse fromAggreage(BuyOrder buyorder){
        return new GetBuyOrderQueryResponse(buyorder.getOrderId().value(), buyorder.getClientId().value(), buyorder.getDate().value(), buyorder.getState().value(),
                buyorder.getTicker().value(), buyorder.getQuantity().value(), buyorder.getPriceToExecute().value());
    }
}
