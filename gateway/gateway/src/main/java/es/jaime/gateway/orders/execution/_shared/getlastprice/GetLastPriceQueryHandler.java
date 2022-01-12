package es.jaime.gateway.orders.execution._shared.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderExecutionPrice;
import org.springframework.stereotype.Service;

@Service
public class GetLastPriceQueryHandler implements QueryHandler<GetLastPriceQuery, GetLastPriceQueryResponse> {
    private final LastPriceService lastPriceStorer;

    public GetLastPriceQueryHandler(LastPriceService lastPriceStorer) {
        this.lastPriceStorer = lastPriceStorer;
    }

    @Override
    public GetLastPriceQueryResponse handle(GetLastPriceQuery query) {
        OrderExecutionPrice lastPrice = lastPriceStorer.getLastPriceFor(query.getTicker());

        return new GetLastPriceQueryResponse(lastPrice);
    }
}
