package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.authentication._shared.domain.UserName;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final GetOrderService getOrderService;

    public GetOrderQueryHandler(GetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        Optional<ActiveOrder> activeOrderOptional = getOrderService.get(query.getActiveOrderID());

        ensureOrderFound(activeOrderOptional);
        ensureUserOwnsTheOrder(activeOrderOptional, query.getUserName());

        var activeOrder = activeOrderOptional.get();

        return new GetOrderQueryResponse(
                activeOrder.activeorderId().value(),
                activeOrder.ticker().value(),
                activeOrder.type().valueString(),
                activeOrder.executionPrice().value(),
                activeOrder.quantity().value(),
                activeOrder.date().value(),
                activeOrder.status().valueString()
        );
    }

    private void ensureOrderFound(Optional<ActiveOrder> optionalActiveOrder){
        if(optionalActiveOrder.isEmpty()){
            throw new ResourceNotFound("Active order for that ID wasn't found!");
        }
    }

    private void ensureUserOwnsTheOrder(Optional<ActiveOrder> optionalActiveOrder, UserName userName){
        if(!userName.value().equals(optionalActiveOrder.get().clientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
