package es.jaime.gateway.orders.checkorder;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
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
                order.orderId().value(),
                order.ticker().value(),
                order.type().valueString(),
                order.executionPrice().value(),
                order.quantity().value(),
                order.date().value()
        );
    }

    private void ensureUserOwnsTheOrder(Order order, UserName userName){
        if(!userName.value().equals(order.clientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
