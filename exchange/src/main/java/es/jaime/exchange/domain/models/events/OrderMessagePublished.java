package es.jaime.exchange.domain.models.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class OrderMessagePublished extends DomainEvent{
    @Getter private List<String> ordersId;

    public OrderMessagePublished(String... ordersId) {
        this.ordersId = Arrays.asList(ordersId);
    }

    public OrderMessagePublished(String orderId) {
        this.ordersId = List.of(orderId);
    }

    //Not needed
    @Override
    public DomainEvent fromPrimitives(Map<String, Object> primitives) {
        return null;
    }

    //Not needed
    @Override
    public String messageId() {
        return null;
    }
}
