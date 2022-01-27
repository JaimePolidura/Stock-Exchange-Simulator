package es.jaime.gateway.orders.pendingorder.execution._shared.application.onerrororder;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder._shared.domain.events.ErrorDuringOrderExecution;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderSaver;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OnExecutionOrderError {
    private final ExecutionOrderFinder executionOrderFinder;
    private final ExecutionOrderSaver executionOrderSaver;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<ExecutionOrder> executionOrderOptional = executionOrderFinder.findById(OrderId.of(event.getOrderId()));

        executionOrderOptional.ifPresent(executionOrder -> {
            executionOrderSaver.changeStateAndSave(executionOrder, OrderState.error());
        });
    }
}
