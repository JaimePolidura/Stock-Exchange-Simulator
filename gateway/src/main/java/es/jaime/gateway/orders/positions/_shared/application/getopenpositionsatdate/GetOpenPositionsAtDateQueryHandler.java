package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions.closed._shared.application.ClosedPositionFinder;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
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
        PositionOpeningDate from = PositionOpeningDate.of(query.getFrom());
        ClosedPositionClosingDate to = ClosedPositionClosingDate.of(query.getTo());

        List<OpenPosition> openPositionList = openPositionFinder.findOpenSince(from);
        List<ClosedPosition> closedPositionsList = closedPositionFinder.findBetweenDate(from, to);

        return new GetOpenPositionsAtDateQueryResponse(new ArrayList<>(){{
            addAll(openPositionList);
            addAll(closedPositionsList);
        }});
    }
}
