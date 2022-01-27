package es.jaime.gateway.orders.pendingorder.execution._shared.application.getorders;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetExecutionOrdersQueryHandler implements QueryHandler<GetExecutionOrdersQuery, GetExecutionOrdersQueryResponse> {
    private final ExecutionOrderFinder executionOrderFinder;

    @Override
    public GetExecutionOrdersQueryResponse handle(GetExecutionOrdersQuery query) {
        List<ExecutionOrder> orders = new ArrayList<>();

        for (OrderState orderState : query.getOrderStates()) {
            orders.addAll(executionOrderFinder.findByClientIdAndState(query.getClientId(), orderState));
        }

        return new GetExecutionOrdersQueryResponse(orders);
    }
}
