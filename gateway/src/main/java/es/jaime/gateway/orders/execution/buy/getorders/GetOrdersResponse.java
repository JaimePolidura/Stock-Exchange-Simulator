package es.jaime.gateway.orders.execution.buy.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.cancel.getorder.GetOrderQueryResponse;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOrdersResponse implements QueryResponse {
    @Getter private final List<GetOrderQueryResponse> orders;

    public static GetOrdersResponse fromAggregateList(List<OrderBuy> aggregateList){
        return new GetOrdersResponse(aggregateList.stream()
                .map(GetOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
