package es.jaime.gateway.orders.pendingorder.execution.sell.getorder;

import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Component;

@Component
public class GetSellOrderQueryHandler implements QueryHandler<GetSellOrderQuery, GetSellOrderQueryResponse> {
    private final SellOrderRepostiry sellOrders;

    public GetSellOrderQueryHandler(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @Override
    public GetSellOrderQueryResponse handle(GetSellOrderQuery query) {
        var order = sellOrders.findByOrderId(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetSellOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(SellOrder sellorder, UserName userName){
        if(!userName.value().equals(sellorder.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
