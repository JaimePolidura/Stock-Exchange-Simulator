package es.jaime.gateway.orders.positions.open.getlastprice;


import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import lombok.Getter;

public final class GetLastPriceQueryResponse implements QueryResponse {
    @Getter private final double lastPrice;

    public GetLastPriceQueryResponse(PositionOpeningPrice executionPrice) {
        this.lastPrice = executionPrice.value();
    }
}
