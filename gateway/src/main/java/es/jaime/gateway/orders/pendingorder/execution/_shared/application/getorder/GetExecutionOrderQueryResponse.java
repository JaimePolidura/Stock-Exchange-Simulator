package es.jaime.gateway.orders.pendingorder.execution._shared.application.getorder;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class GetExecutionOrderQueryResponse implements QueryResponse {
    @Getter private final ExecutionOrder executionOrder;

    public Map<String, Object> toPrimitives(){
        return this.executionOrder.toPrimitives();
    }
}
