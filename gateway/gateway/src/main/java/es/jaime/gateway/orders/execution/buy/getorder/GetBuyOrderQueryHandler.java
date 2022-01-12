package es.jaime.gateway.orders.execution.buy.getorder;

import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import org.springframework.stereotype.Component;

@Component
public class GetBuyOrderQueryHandler implements QueryHandler<GetBuyOrderQuery, GetBuyOrderQueryResponse> {
    private final BuyOrderRepostory buyOrders;

    public GetBuyOrderQueryHandler(BuyOrderRepostory buyOrders) {
        this.buyOrders = buyOrders;
    }

    @Override
    public GetBuyOrderQueryResponse handle(GetBuyOrderQuery query) {
        var order = buyOrders.findByOrderId(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetBuyOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(BuyOrder orderBuy, UserName userName){
        if(!userName.value().equals(orderBuy.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
