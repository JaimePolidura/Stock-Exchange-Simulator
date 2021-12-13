package es.jaime.exchange.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public final class Order implements Comparable<Order>{
    @Getter private final UUID orderId;
    @Getter private final UUID clientId;
    @Getter private final LocalDateTime date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final OrderType type;

    @Override
    public int compareTo(Order o) {
        return 0;
    }
}
