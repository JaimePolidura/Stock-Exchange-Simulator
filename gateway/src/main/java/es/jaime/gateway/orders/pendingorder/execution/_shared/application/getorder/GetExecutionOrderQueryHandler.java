package es.jaime.gateway.orders.pendingorder.execution._shared.application.getorder;

import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetExecutionOrderQueryHandler implements QueryHandler<GetExecutionOrderQuery, GetExecutionOrderQueryResponse> {
    private final ExecutionOrderFinder executionOrderFinder;

    @Override
    public GetExecutionOrderQueryResponse handle(GetExecutionOrderQuery query) {
        var order = executionOrderFinder.findById(query.getOrderId())
                .orElseThrow(() -> new ResourceNotFound("Order for that ID wasn't found!"));

        ensureOrderOwned(order, query.getClientId());

        return new GetExecutionOrderQueryResponse(order);
    }

    private void ensureOrderOwned(ExecutionOrder order, OrderClientId clientId){
        if(!order.getClientId().value().equalsIgnoreCase(clientId.value())){
            throw new NotTheOwner("You dont own that order");
        }
    }
}
