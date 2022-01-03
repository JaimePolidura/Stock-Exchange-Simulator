package es.jaime.gateway.orders.orders.getorderid;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class GetOrderQueryResponse implements QueryResponse {
    @Getter private final String orderId;
    @Getter private final String date;
    @Getter private final int orderTypeId;
    @Getter private final Map<String, Object> body;

    public static GetOrderQueryResponse fromAggreage(Order order){
        return new GetOrderQueryResponse(order.getOrderId().value(), order.getDate().value(),
                order.getOrderTypeId().value(),order.getBody().toMap());
    }
}
