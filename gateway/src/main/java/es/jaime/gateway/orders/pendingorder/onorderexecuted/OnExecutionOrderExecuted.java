package es.jaime.gateway.orders.pendingorder.onorderexecuted;

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
        System.out.println("idk");

        Optional<ExecutionOrder> executionOrderOptional = executionOrderFinder.findById(OrderId.of(event.getOrderId()));

        if(executionOrderOptional.isEmpty()){
            System.err.println("Order not found " + event.getOrderId());
            return;
        }

        ExecutionOrder orderExecuged = executionOrderOptional.get();

        int actualQuantity = orderExecuged.getQuantity().value();
        int quantityBought = event.getQuantity();
        OrderQuantity newQuantity = OrderQuantity.of(actualQuantity - quantityBought);

        if(actualQuantity == quantityBought)
            executionOrderDeleter.deleteById(orderExecuged);
        else
            executionOrderSaver.changeQuantityAndSave(orderExecuged, newQuantity);
    }
}
