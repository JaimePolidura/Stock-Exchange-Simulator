package es.jaime.gateway.orders.execution.buy.getorder;

import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final BuyOrderRepostiry buyOrders;

    public GetOrderQueryHandler(BuyOrderRepostiry buyOrders) {
        this.buyOrders = buyOrders;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        var order = buyOrders.findByOrderId(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(OrderBuy orderBuy, UserName userName){
        if(!userName.value().equals(orderBuy.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
