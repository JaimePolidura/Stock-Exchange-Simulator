package es.jaime.gateway.orders.execution.buy.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.execution.buy.getorder.GetBuyOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetBuyOrdersResponse implements QueryResponse {
    @Getter private final List<GetBuyOrderQueryResponse> orders;

    public static GetBuyOrdersResponse fromAggregateList(List<BuyOrder> aggregateList){
        return new GetBuyOrdersResponse(aggregateList.stream()
                .map(GetBuyOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
