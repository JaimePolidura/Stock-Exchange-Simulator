package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.models.events.OrderArrived;
import es.jaime.exchange.domain.models.events.OrderMessagePublished;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActiveOrderStorer {
    private final Set<String> messageOrdersId;

    public ActiveOrderStorer (){
        this.messageOrdersId = new HashSet<>();
    }

    public boolean contains(String id){
        return messageOrdersId.contains(id);
    }

    @EventListener({OrderArrived.class})
    public void onNewOrder(OrderArrived event){
        this.messageOrdersId.add(event.getOrderId());
    }

    @EventListener({OrderMessagePublished.class})
    public void on(OrderMessagePublished event){
        event.getOrdersId().forEach(this.messageOrdersId::remove);
    }
}
