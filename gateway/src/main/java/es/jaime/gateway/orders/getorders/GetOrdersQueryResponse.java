package es.jaime.gateway.orders.getorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders.getorder.GetOrderQuery;
import es.jaime.gateway.orders.getorder.GetOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOrdersQueryResponse implements QueryResponse {
    @Getter private final List<GetOrderQueryResponse> orders;

    public static GetOrdersQueryResponse fromAggregateList(List<Order> aggregateList){
        return new GetOrdersQueryResponse(aggregateList.stream()
                .map(GetOrderQueryResponse::fromAggreage)
                .collect(Collectors.toList()));
    }
}
