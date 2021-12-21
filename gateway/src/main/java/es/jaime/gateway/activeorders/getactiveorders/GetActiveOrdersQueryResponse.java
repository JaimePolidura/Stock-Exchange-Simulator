package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetActiveOrdersQueryResponse implements QueryResponse {
    @Getter private final List<Map<String, Object>> activeOrdersPrimitives;

    public static GetActiveOrdersQueryResponse fromAggregateList (List<ActiveOrder> aggregateList) {
        return new GetActiveOrdersQueryResponse(aggregateList.stream()
                .map(ActiveOrder::toPrimitives)
                .collect(Collectors.toList()));
    }
}
