package es.jaime.gateway.orders.orders.getorderstype;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.ordertypes.application.OrdersTypesFinder;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersQueryHandler implements QueryHandler<GetOrdersByTypeQuery, GetOrdersQueryResponse> {
    private final OrdersRepository activeOrders;
    private final OrdersTypesFinder ordersTypesFinder;

    public GetOrdersQueryHandler(OrdersRepository repository, OrdersTypesFinder ordersTypesFinder) {
        this.activeOrders = repository;
        this.ordersTypesFinder = ordersTypesFinder;
    }

    @Override
    public GetOrdersQueryResponse handle(GetOrdersByTypeQuery query) {
        ensureOrderTypesIdValid(query.getOrderTypeIds());

        List<Order> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderTypeId orderTypeId : query.getOrderTypeIds()) {
            allResults.addAll(activeOrders.findOrdersByTypeAndClientId(orderTypeId, clientId));
        }

        return GetOrdersQueryResponse.fromAggregateList(allResults);
    }

    private void ensureOrderTypesIdValid(List<OrderTypeId> orderTypeIds){
        for (var orderTypeId : orderTypeIds) {
            var exists = ordersTypesFinder.existsTypes(orderTypeId);

            if(!exists)
                throw new ResourceNotFound("order type id "+orderTypeId.value()+" not found");
        }
    }
}
