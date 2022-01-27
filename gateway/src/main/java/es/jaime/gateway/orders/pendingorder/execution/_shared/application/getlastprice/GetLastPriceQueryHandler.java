package es.jaime.gateway.orders.pendingorder.execution._shared.application.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetLastPriceQueryHandler implements QueryHandler<GetLastPriceQuery, GetLastPriceQueryResponse> {
    private final LastPriceService lastPriceStorer;

    @Override
    public GetLastPriceQueryResponse handle(GetLastPriceQuery query) {
        OrderPriceToExecute lastPrice = lastPriceStorer.getLastPriceFor(query.getTicker());

        return new GetLastPriceQueryResponse(lastPrice);
    }
}