package es.jaime.gateway._shared.domain.bus.query;

public interface QueryBus {
    <R extends QueryResponse> R ask(Query query);
}
