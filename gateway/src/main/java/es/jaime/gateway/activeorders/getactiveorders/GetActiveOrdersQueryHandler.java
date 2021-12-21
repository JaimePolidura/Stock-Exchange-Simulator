package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;

import java.util.List;

public class GetActiveOrdersQueryHandler implements QueryHandler<GetActiveOrdersQuery, GetActiveOrdersQueryResponse> {
    private final ActiveOrderRepository repository;

    public GetActiveOrdersQueryHandler(ActiveOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetActiveOrdersQueryResponse handle(GetActiveOrdersQuery query) {
        List<ActiveOrder> activeOrders = repository.findByClientId(query.getClientID())
                .orElseThrow(() -> new ResourceNotFound("No orders found for you"));

        return GetActiveOrdersQueryResponse.fromAggregateList(activeOrders);
    }
}
