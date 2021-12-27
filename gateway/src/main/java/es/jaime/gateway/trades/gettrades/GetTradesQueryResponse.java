package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetTradesQueryResponse implements QueryResponse {
    @AllArgsConstructor
    private static class GetTradeQueryResponse {
        @Getter private final String ticker;
        @Getter private final double averagePrice;
        @Getter private final double actualPrice;
        @Getter private final double quantity;
    }
}
