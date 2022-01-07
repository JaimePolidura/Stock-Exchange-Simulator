package es.jaime.gateway.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOpenPositionsQueryHandler implements QueryHandler<GetOpenPositionsQuery, GetOpenPositionsQueryResponse> {
    private final OpenPositionsRepository repository;

    public GetOpenPositionsQueryHandler(OpenPositionsRepository tradesRepository) {
        this.repository = tradesRepository;
    }

    @Override
    public GetOpenPositionsQueryResponse handle(GetOpenPositionsQuery query) {
        List<OpenPosition> trades = repository.findByClientId(query.getClientId());

        return GetOpenPositionsQueryResponse.fromAggregateList(trades);
    }
}
