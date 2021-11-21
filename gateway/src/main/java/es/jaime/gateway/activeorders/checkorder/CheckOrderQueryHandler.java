package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import es.jaime.gateway.authentication._shared.domain.UserName;

import java.util.Optional;

public final class CheckOrderQueryHandler implements QueryHandler<CheckOrderQuery, CheckOrderQueryResponse> {
    private final ActiveOrderRepository repository;

    public CheckOrderQueryHandler(ActiveOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public CheckOrderQueryResponse handle(CheckOrderQuery query) {
        Optional<ActiveOrder> activeOrderOptional = repository.findByOrderId(query.getActiveOrderID());

        ensureOrderFound(activeOrderOptional);
        ensureUserOwnsTheOrder(activeOrderOptional, query.getUserName());

        var activeOrder = activeOrderOptional.get();

        return null;
    }

    private void ensureOrderFound(Optional<ActiveOrder> optionalActiveOrder){
        if(optionalActiveOrder.isEmpty()){
            throw new ResourceNotFound("Active order for that ID wasn't found!");
        }
    }

    private void ensureUserOwnsTheOrder(Optional<ActiveOrder> optionalActiveOrder, UserName userName){
        if(!userName.toString().equals(optionalActiveOrder.get().clientId().toString())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
