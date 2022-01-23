package es.jaime.exchange.domain.models.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class OrderMessagePublished extends DomainEvent{
    @Getter private final List<String> ordersId;

    public OrderMessagePublished(String... ordersId) {
        this.ordersId = Arrays.asList(ordersId);
    }

    public OrderMessagePublished(String orderId) {
        this.ordersId = List.of(orderId);
    }
}
