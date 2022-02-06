package es.jaime.gateway.orders.positions.open.getlastprice;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import lombok.Getter;

public final class GetLastPriceQuery implements Query {
    @Getter private final OrderTicker ticker;

    public GetLastPriceQuery(String ticker) {
        this.ticker = OrderTicker.of(ticker);
    }
}
