package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.trades._shared.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetTradesQueryResponse implements QueryResponse {
    @Getter private final List<GetTradeQueryResponse> trades;

    public static GetTradesQueryResponse fromAggregateList(List<Trade> trades){
        return new GetTradesQueryResponse(trades.stream()
                .map(GetTradeQueryResponse::fromAggregate)
                .collect(Collectors.toList()));
    }

    @AllArgsConstructor
    private static class GetTradeQueryResponse {
        @Getter private final String tradeId;
        @Getter private final String ticker;
        @Getter private final String openingDate;
        @Getter private double openingPrice;
        @Getter private int quantity;

        private static GetTradeQueryResponse fromAggregate(Trade aggregate){
            return new GetTradeQueryResponse(aggregate.getTradeId().value(), aggregate.getTicker().value(),
                    aggregate.getOpeningDate().value(), aggregate.getOpeningPrice().value(), aggregate.getQuantity().value());
        }
    }
}
