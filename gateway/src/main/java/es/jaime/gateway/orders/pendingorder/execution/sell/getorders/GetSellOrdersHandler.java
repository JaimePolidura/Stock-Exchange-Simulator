package es.jaime.gateway.orders.pendingorder.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetSellOrdersHandler implements QueryHandler<GetSellOrdersQuery, GetSellOrdersResponse> {
    private final SellOrderRepostiry sellOrders;

    public GetSellOrdersHandler(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @Override
    public GetSellOrdersResponse handle(GetSellOrdersQuery query) {
        List<SellOrder> allResults = new ArrayList<>();
        OrderClientId clientId = query.getClientId();

        for (OrderState orderState : query.getOrderStates()) {
            allResults.addAll(sellOrders.findByOrderClientIdAndState(clientId, orderState));
        }

        return GetSellOrdersResponse.fromAggregateList(allResults);
    }
}
