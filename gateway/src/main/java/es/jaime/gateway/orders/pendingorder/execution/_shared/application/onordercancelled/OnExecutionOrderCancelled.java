package es.jaime.gateway.orders.pendingorder.execution._shared.application.onordercancelled;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderCancelled;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderSaver;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OnExecutionOrderCancelled {
    private final ExecutionOrderSaver executionOrderSaver;
    private final ExecutionOrderFinder executionOrderFinder;

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled event){
        Optional<ExecutionOrder> executionOrderOptional = executionOrderFinder.findById(OrderId.of(event.getOrderIdCancelled()));

        executionOrderOptional.ifPresent(executionOrder -> {
            executionOrderSaver.changeStateAndSave(executionOrder, OrderState.cancelled());
        });
    }
}
