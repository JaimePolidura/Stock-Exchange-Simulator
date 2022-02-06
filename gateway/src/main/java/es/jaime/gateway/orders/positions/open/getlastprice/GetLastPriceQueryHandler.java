package es.jaime.gateway.orders.positions.open.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetLastPriceQueryHandler implements QueryHandler<GetLastPriceQuery, GetLastPriceQueryResponse> {
    private final LastPriceService lastPriceStorer;

    @Override
    public GetLastPriceQueryResponse handle(GetLastPriceQuery query) {
        PositionOpeningPrice lastPrice = lastPriceStorer.getLastPriceFor(query.getTicker());

        return new GetLastPriceQueryResponse(lastPrice);
    }
}
