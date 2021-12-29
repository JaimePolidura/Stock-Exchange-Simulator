package es.jaime.gateway.orders.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders._shared.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetOrderQueryResponse implements QueryResponse {
    @Getter private final String orderId;
    @Getter private final String ticker;
    @Getter private final String type;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String date;

    public static GetOrderQueryResponse fromAggreage(Order order){
        return new GetOrderQueryResponse(order.getOrderId().value(), order.getTicker().value(), order.getType().valueString(),
                order.getExecutionPrice().value(), order.getQuantity().value(), order.getDate().value());
    }
}
