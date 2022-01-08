package es.jaime.gateway.orders.execution.sell.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetOrderQueryResponse implements QueryResponse {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final String state;
    @Getter private final String ticker;
    @Getter private final int quantity;
    @Getter private final double executionPrice;
    @Getter private final String positionIdToSell;

    public static GetOrderQueryResponse fromAggreage(OrderSell sellOrder){
        return new GetOrderQueryResponse(sellOrder.getOrderId().value(), sellOrder.getClientId().value(), sellOrder.getDate().value(), sellOrder.getState().value(),
                sellOrder.getTicker().value(), sellOrder.getQuantity().value(), sellOrder.getExecutionPrice().value(), sellOrder.getPositionIdToSell().value());
    }
}
