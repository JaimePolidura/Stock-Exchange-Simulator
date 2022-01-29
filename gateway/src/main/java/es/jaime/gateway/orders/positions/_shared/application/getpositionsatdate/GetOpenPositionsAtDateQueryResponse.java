package es.jaime.gateway.orders.positions._shared.application.getpositionsatdate;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.positions._shared.domain.Position;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOpenPositionsAtDateQueryResponse implements QueryResponse {
    private final List<Position> positions;

    public List<Map<String, Object>> toPrimitives(){
        return this.positions.stream()
                .map(Position::toPrimitives)
                .collect(Collectors.toList());
    }
}
