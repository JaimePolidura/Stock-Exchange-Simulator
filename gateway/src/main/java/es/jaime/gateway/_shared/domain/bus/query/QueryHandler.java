package es.jaime.gateway._shared.domain.bus.query;

public interface QueryHandler<Q extends Query, R extends QueryResponse> {
    R handle(Q query);
}
