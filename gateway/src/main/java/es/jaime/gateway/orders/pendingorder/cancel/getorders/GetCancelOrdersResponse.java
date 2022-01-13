package es.jaime.gateway.orders.pendingorder.cancel.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel.getorder.GetCancelOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetCancelOrdersResponse implements QueryResponse {
    @Getter private final List<GetCancelOrderQueryResponse> orders;

    public static GetCancelOrdersResponse fromAggregateList(List<CancelOrder> aggregateList){
        return new GetCancelOrdersResponse(aggregateList.stream()
                .map(GetCancelOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
