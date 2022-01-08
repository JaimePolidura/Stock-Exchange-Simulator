package es.jaime.gateway.orders.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell.getorder.GetOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOrdersResponse implements QueryResponse {
    @Getter private final List<GetOrderQueryResponse> orders;

    public static GetOrdersResponse fromAggregateList(List<OrderSell> aggregateList){
        return new GetOrdersResponse(aggregateList.stream()
                .map(GetOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
