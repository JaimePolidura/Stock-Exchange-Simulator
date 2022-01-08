package es.jaime.gateway.orders.cancel.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.cancel._shared.domain.CancelOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetCancelOrderQueryResponse implements QueryResponse {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final String state;
    @Getter private final String ticker;
    @Getter private final String orderIdToCancel;

    public static GetCancelOrderQueryResponse fromAggreage(CancelOrder order){
        return new GetCancelOrderQueryResponse(order.getOrderId().value(), order.getClientId().value(), order.getDate().value(),
                order.getState().value(), order.getTicker().value(), order.getOrderIdToCancel().value());
    }
}
