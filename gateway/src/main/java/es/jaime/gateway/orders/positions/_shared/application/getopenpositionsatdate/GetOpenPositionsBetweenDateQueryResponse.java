package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.positions._shared.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOpenPositionsBetweenDateQueryResponse implements QueryResponse {
    @Getter private final List<Position> positionList;

    public List<Map<String, Object>> toPrimitives(){
        return positionList.stream()
                .map(Aggregate::toPrimitives)
                .collect(Collectors.toList());
    }
}
