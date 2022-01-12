package es.jaime.gateway.orders.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.sell.getorder.GetSellOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetSellOrdersResponse implements QueryResponse {
    @Getter private final List<GetSellOrderQueryResponse> orders;

    public static GetSellOrdersResponse fromAggregateList(List<SellOrder> aggregateList){
        return new GetSellOrdersResponse(aggregateList.stream()
                .map(GetSellOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
