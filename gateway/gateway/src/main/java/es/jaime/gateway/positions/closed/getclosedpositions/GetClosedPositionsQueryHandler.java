package es.jaime.gateway.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.positions.closed._shared.domain.ClosedPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetClosedPositionsQueryHandler implements QueryHandler<GetClosedPositionsQuery, GetClosedPositionsQueryResponse> {
    private final ClosedPositionRepository closedPositions;

    public GetClosedPositionsQueryHandler(ClosedPositionRepository closedPositions) {
        this.closedPositions = closedPositions;
    }

    @Override
    public GetClosedPositionsQueryResponse handle(GetClosedPositionsQuery query) {
        List<ClosedPosition> closedPositions = this.closedPositions.findByClientId(query.getClientId());

        return GetClosedPositionsQueryResponse.fromAggregateList(closedPositions);
    }
}
