package es.jaime.gateway.orders.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetClosedPositionsQueryResponse implements QueryResponse {
    @Getter private final List<GetClosedPosition> closedPositions;

    public static GetClosedPositionsQueryResponse fromAggregateList(List<ClosedPosition> closedPositions){
        return new GetClosedPositionsQueryResponse(closedPositions.stream()
                .map(GetClosedPosition::fromAggregate)
                .collect(Collectors.toList()));
    }

    @AllArgsConstructor
    private static final class GetClosedPosition  {
        @Getter private final String closedPositionId;
        @Getter private final String ticker;
        @Getter private final int quantity;
        @Getter private final double openingPrice;
        @Getter private final String openingDate;
        @Getter private final double closingPrice;
        @Getter private final String closingDate;

        public static GetClosedPosition fromAggregate(ClosedPosition closedPosition) {
            return new GetClosedPosition(closedPosition.getPositionId().value(), closedPosition.getTicker().value(),
                    closedPosition.getQuantity().value(), closedPosition.getOpeningPrice().value(), closedPosition.getOpeningDate().value(),
                    closedPosition.getClosingPrice().value(), closedPosition.getClosingDate().value());
        }
    }
}
