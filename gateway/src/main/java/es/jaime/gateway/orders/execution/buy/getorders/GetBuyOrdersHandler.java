package es.jaime.gateway.orders.execution.buy.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetBuyOrdersHandler implements QueryHandler<GetBuyOrdersQuery, GetBuyOrdersResponse> {
    private final BuyOrderRepostiry buyOrders;

    public GetBuyOrdersHandler(BuyOrderRepostiry buyOrders) {
        this.buyOrders = buyOrders;
    }

    @Override
    public GetBuyOrdersResponse handle(GetBuyOrdersQuery query) {
        List<BuyOrder> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(buyOrders.findByOrderClientIdAndState(clientId, orderState));
        }

        return GetBuyOrdersResponse.fromAggregateList(allResults);
    }
}
