package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTradesQueryHandler implements QueryHandler<GetTradesQuery, GetTradesQueryResponse> {
    private final TradesRepository repository;

    public GetTradesQueryHandler(TradesRepository tradesRepository) {
        this.repository = tradesRepository;
    }

    @Override
    public GetTradesQueryResponse handle(GetTradesQuery query) {
        List<Trade> allTrades = repository.findTradesByClientId(query.getClientId())
                .orElseThrow(() -> new ResourceNotFound("No trades found for you"));

        return null;
    }
}
