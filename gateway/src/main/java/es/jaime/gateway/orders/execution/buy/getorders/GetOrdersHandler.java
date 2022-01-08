package es.jaime.gateway.orders.execution.buy.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersHandler implements QueryHandler<GetOrdersQuery, GetOrdersResponse> {
    private final BuyOrderRepostiry buyOrders;

    public GetOrdersHandler(BuyOrderRepostiry buyOrders) {
        this.buyOrders = buyOrders;
    }

    @Override
    public GetOrdersResponse handle(GetOrdersQuery query) {
        List<OrderBuy> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(buyOrders.findByOrderClientIdAndState(clientId, orderState));
        }

        return GetOrdersResponse.fromAggregateList(allResults);
    }
}
