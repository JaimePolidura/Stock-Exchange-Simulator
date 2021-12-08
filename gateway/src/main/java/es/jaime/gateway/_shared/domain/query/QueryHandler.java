package es.jaime.gateway._shared.domain.query;

public interface QueryHandler<Q extends Query, R extends QueryResponse> {
    R handle(Q query);
}
