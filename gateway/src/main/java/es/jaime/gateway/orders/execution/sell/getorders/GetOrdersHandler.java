package es.jaime.gateway.orders.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersHandler implements QueryHandler<GetOrdersQuery, GetOrdersResponse> {
    private final SellOrderRepostiry sellOrders;

    public GetOrdersHandler(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @Override
    public GetOrdersResponse handle(GetOrdersQuery query) {
        List<OrderSell> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(sellOrders.findByOrderClientIdAndState(clientId, orderState));
        }

        return GetOrdersResponse.fromAggregateList(allResults);
    }
}
