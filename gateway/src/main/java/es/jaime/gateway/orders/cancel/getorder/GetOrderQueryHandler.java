package es.jaime.gateway.orders.cancel.getorder;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.cancel._shared.domain.OrderCancel;
import es.jaime.gateway.orders.cancel._shared.domain.OrdersCancelRepository;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final OrdersCancelRepository cancelOrders;

    public GetOrderQueryHandler(OrdersCancelRepository cancelOrders) {
        this.cancelOrders = cancelOrders;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        var order = cancelOrders.findById(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(OrderCancel order, UserName userName){
        if(!userName.value().equals(order.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
