package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.QueryResponse;
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
    @Getter private final String status;
}
