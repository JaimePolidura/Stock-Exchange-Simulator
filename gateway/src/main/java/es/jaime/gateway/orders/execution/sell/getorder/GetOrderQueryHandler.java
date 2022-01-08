package es.jaime.gateway.orders.execution.sell.getorder;

import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final SellOrderRepostiry sellOrders;

    public GetOrderQueryHandler(SellOrderRepostiry sellOrders) {
        this.sellOrders = sellOrders;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        var order = sellOrders.findByOrderId(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(OrderSell sellorder, UserName userName){
        if(!userName.value().equals(sellorder.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
