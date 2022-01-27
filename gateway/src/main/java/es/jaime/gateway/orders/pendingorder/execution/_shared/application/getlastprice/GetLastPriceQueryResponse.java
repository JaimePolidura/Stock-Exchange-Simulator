package es.jaime.gateway.orders.pendingorder.execution._shared.application.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.Getter;

public final class GetLastPriceQueryResponse implements QueryResponse {
    @Getter private final double lastPrice;

    public GetLastPriceQueryResponse(OrderPriceToExecute executionPrice) {
        this.lastPrice = executionPrice.value();
    }
}