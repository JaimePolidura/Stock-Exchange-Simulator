package es.jaime.gateway.orders.cancel.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.cancel._shared.domain.OrdersCancelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCancelOrdersHandler implements QueryHandler<GetCancelOrdersQuery, GetCancelOrdersResponse> {
    private final OrdersCancelRepository ordersCancel;

    public GetCancelOrdersHandler(OrdersCancelRepository ordersCancel) {
        this.ordersCancel = ordersCancel;
    }

    @Override
    public GetCancelOrdersResponse handle(GetCancelOrdersQuery query) {
        List<CancelOrder> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(ordersCancel.findByClientIdAndOrderState(clientId, orderState));
        }

        return GetCancelOrdersResponse.fromAggregateList(allResults);
    }
}
