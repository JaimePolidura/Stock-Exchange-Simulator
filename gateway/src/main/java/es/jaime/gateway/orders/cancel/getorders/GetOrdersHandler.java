package es.jaime.gateway.orders.cancel.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.cancel._shared.domain.OrderCancel;
import es.jaime.gateway.orders.cancel._shared.domain.OrdersCancelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersHandler implements QueryHandler<GetOrdersQuery, GetOrdersResponse> {
    private final OrdersCancelRepository ordersCancel;

    public GetOrdersHandler(OrdersCancelRepository ordersCancel) {
        this.ordersCancel = ordersCancel;
    }

    @Override
    public GetOrdersResponse handle(GetOrdersQuery query) {
        List<OrderCancel> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(ordersCancel.findByOrderStateAndClientId(orderState, clientId));
        }

        return GetOrdersResponse.fromAggregateList(allResults);
    }
}
