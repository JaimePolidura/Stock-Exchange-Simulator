package es.jaime.gateway.orders.getorder;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.authentication._shared.domain.UserName;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final GetOrderService getOrderService;

    public GetOrderQueryHandler(GetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        var order = getOrderService.get(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(order, query.getUserName());

        return new GetOrderQueryResponse(
                order.getOrderId().value(),
                order.getTicker().value(),
                order.getType().valueString(),
                order.getExecutionPrice().value(),
                order.getQuantity().value(),
                order.getDate().value()
        );
    }

    private void ensureUserOwnsTheOrder(Order order, UserName userName){
        if(!userName.value().equals(order.getClientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
