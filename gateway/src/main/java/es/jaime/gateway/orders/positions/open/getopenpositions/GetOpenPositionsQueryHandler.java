package es.jaime.gateway.orders.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetOpenPositionsQueryHandler implements QueryHandler<GetOpenPositionsQuery, GetOpenPositionsQueryResponse> {
    private final OpenPositionsRepository repository;

    @Override
    public GetOpenPositionsQueryResponse handle(GetOpenPositionsQuery query) {
        List<OpenPosition> trades = repository.findByClientId(query.getClientId());

        return GetOpenPositionsQueryResponse.fromAggregateList(trades);
    }
}
