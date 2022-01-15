package es.jaime.gateway.orders.pendingorder.execution.getorders;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetExecutionOrdersQueryResponse implements QueryResponse {
    @Getter private final List<ExecutionOrder> executionOrders;

    public List<Map<String, Object>> toPrimitives(){
        return executionOrders.stream()
                .map(ExecutionOrder::toPrimitives)
                .collect(Collectors.toList());
    }
}
