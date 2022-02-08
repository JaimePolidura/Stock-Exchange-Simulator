package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.models.events.OrderArrived;
import es.jaime.exchange.domain.models.events.OrderMessagePublished;
import es.jaime.exchange.infrastructure.activeorders.ActiveOrderRepositoryInRedis;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActiveOrderStorer {
    private final ActiveOrderRepositoryInRedis activeOrders;

    public boolean contains(String id){
        return activeOrders.existsByOrderId(id);
    }

    @EventListener({OrderArrived.class})
    public void onNewOrder(OrderArrived event){
        activeOrders.save(event.getOrderId());
    }

    @EventListener({OrderMessagePublished.class})
    public void on(OrderMessagePublished event){
        event.getOrdersId().forEach(this.activeOrders::removeByOrderId);
    }
}
