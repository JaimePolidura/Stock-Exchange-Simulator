package es.jaime.gateway.orders.pendingorder.execution._shared.application.onexecutionorderexecuted;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OnExecutionOrderExecuted {
    private final ExecutionOrderFinder executionOrderFinder;
    private final ExecutionOrderDeleter executionOrderDeleter;
    private final ExecutionOrderSaver executionOrderSaver;

    @EventListener({ExecutionOrderExecuted.class})
    public void on(ExecutionOrderExecuted event){
        Optional<ExecutionOrder> executionOrderOptional = executionOrderFinder.findById(OrderId.of(event.getOrderId()));

        if(executionOrderOptional.isEmpty()){
            System.err.println("Order not found " + event.getOrderId());
            return;
        }

        ExecutionOrder orderExecuted = executionOrderOptional.get();

        int actualQuantity = orderExecuted.getQuantity().value();
        int quantityBought = event.getQuantity();
        OrderQuantity newQuantity = OrderQuantity.of(actualQuantity - quantityBought);

        if(actualQuantity == quantityBought)
            executionOrderDeleter.deleteById(orderExecuted);
        else
            executionOrderSaver.changeQuantityAndSave(orderExecuted, newQuantity);
    }
}
