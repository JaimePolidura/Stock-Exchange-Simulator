package es.jaime.gateway.orders.orders.getorderid;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders.orders._shared.application.OrderFinderService;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final OrderFinderService getOrderService;

    public GetOrderQueryHandler(OrderFinderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        var order = getOrderService.findByOrderId(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return GetOrderQueryResponse.fromAggreage(order);
    }

    private void ensureUserOwnsTheOrder(Order order, UserName userName){
        if(!userName.value().equals(order.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
