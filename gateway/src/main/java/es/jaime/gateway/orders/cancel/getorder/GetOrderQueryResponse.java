package es.jaime.gateway.orders.cancel.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
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

    public static GetOrderQueryResponse fromAggreage(OrderBuy order){
        return new GetOrderQueryResponse(order.getOrderId().value(), order.getClientId().value(), order.getDate().value(),
                order.getState().value(), order.getTicker().value(), order.getQuantity().value(), order.getExecutionPrice().value());
    }
}
