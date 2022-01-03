package es.jaime.gateway.orders.orders.getorderstype;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersQueryHandler implements QueryHandler<GetOrdersByTypeQuery, GetOrdersQueryResponse> {
    private final OrdersRepository activeOrders;

    public GetOrdersQueryHandler(OrdersRepository repository) {
        this.activeOrders = repository;;
    }

    @Override
    public GetOrdersQueryResponse handle(GetOrdersByTypeQuery query) {
        List<Order> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderTypeId orderTypeId : query.getTypes()) {
            allResults.addAll(activeOrders.findOrdersByTypeAndClientId(orderTypeId, clientId));
        }

        return GetOrdersQueryResponse.fromAggregateList(allResults);
    }
}
