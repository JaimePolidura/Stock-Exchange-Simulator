package es.jaime.gateway.orders.execution._shared.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders._shared.domain.OrderExecutionPrice;
import lombok.Getter;

public final class GetLastPriceQueryResponse implements QueryResponse {
    @Getter private final double lastPrice;

    public GetLastPriceQueryResponse(OrderExecutionPrice executionPrice) {
        this.lastPrice = executionPrice.value();
    }
}
