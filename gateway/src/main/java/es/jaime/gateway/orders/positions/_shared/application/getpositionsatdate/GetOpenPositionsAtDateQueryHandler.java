package es.jaime.gateway.orders.positions._shared.application.getpositionsatdate;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions.closed._shared.application.ClosedPositionFinder;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetOpenPositionsAtDateQueryHandler implements QueryHandler<GetOpenPositionsAtDateQuery, GetOpenPositionsAtDateQueryResponse> {
    private final OpenPositionFinder openPositionFinder;
    private final ClosedPositionFinder closedPositionFinder;

    @Override
    public GetOpenPositionsAtDateQueryResponse handle(GetOpenPositionsAtDateQuery query) {
        PositionOpeningDate openingDate = query.getOpeningDate();
        OrderClientId clientId = query.getClientId();

        List<OpenPosition> openPosition = openPositionFinder.findOpenByClientIdAndSince(clientId, openingDate);
        List<ClosedPosition> closedPositions = closedPositionFinder.findByClientIdAndOpeningClosingDateBetween(clientId, openingDate);

        return new GetOpenPositionsAtDateQueryResponse(new ArrayList<>(){{
            addAll(openPosition);
            addAll(closedPositions);
        }});
    }
}
