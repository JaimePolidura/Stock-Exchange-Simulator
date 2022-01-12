package es.jaime.gateway._shared.infrastrocture.bus.query;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.domain.query.QueryResponse;
import org.springframework.stereotype.Service;

@Service
public class InMemoryQueryBus implements QueryBus {
    private final QueryHandlerMapper queryHandlerMapper;

    public InMemoryQueryBus(QueryHandlerMapper queryHandlerMapper) {
        this.queryHandlerMapper = queryHandlerMapper;
    }

    @Override
    public <R extends QueryResponse> R ask(Query query) {
        return (R) this.queryHandlerMapper.search(query.getClass()).handle(query);
    }
}
