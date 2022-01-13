package es.jaime.gateway.orders.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOpenPositionsQueryResponse implements QueryResponse {
    @Getter private final List<GetTradeQueryResponse> openPositions;

    public static GetOpenPositionsQueryResponse fromAggregateList(List<OpenPosition> trades){
        return new GetOpenPositionsQueryResponse(trades.stream()
                .map(GetTradeQueryResponse::fromAggregate)
                .collect(Collectors.toList()));
    }

    @AllArgsConstructor
    private static class GetTradeQueryResponse {
        @Getter private final String positionId;
        @Getter private final String ticker;
        @Getter private final String openingDate;
        @Getter private double openingPrice;
        @Getter private int quantity;

        private static GetTradeQueryResponse fromAggregate(OpenPosition aggregate){
            return new GetTradeQueryResponse(aggregate.getOrderId().value(), aggregate.getTicker().value(),
                    aggregate.getOpeningDate().value(), aggregate.getOpeningPrice().value(), aggregate.getQuantity().value());
        }
    }
}
