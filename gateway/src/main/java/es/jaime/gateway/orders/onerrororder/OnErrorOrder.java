package es.jaime.gateway.orders.onerrororder;

import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders._shared.domain.events.ErrorDuringOrderExecution;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnErrorOrder {
    private final OrdersRepository ordersRepository;

    public OnErrorOrder(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        ordersRepository.deleteByOrderId(OrderID.of(event.getOrderId()));
    }
}
