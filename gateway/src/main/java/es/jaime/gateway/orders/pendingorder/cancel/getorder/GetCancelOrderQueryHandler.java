package es.jaime.gateway.orders.pendingorder.cancel.getorder;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import org.springframework.stereotype.Component;

@Component
public class GetCancelOrderQueryHandler implements QueryHandler<GetCancelOrderQuery, GetCancelOrderQueryResponse> {
    private final OrdersCancelRepository cancelOrders;

    public GetCancelOrderQueryHandler(OrdersCancelRepository cancelOrders) {
        this.cancelOrders = cancelOrders;
    }

    @Override
    public GetCancelOrderQueryResponse handle(GetCancelOrderQuery query) {
        var order = cancelOrders.findById(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetCancelOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(CancelOrder order, OrderClientId clientId){
        if(!clientId.value().equals(order.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
