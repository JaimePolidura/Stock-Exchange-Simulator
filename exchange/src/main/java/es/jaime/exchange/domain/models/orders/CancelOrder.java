package es.jaime.exchange.domain.models.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CancelOrder {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String orderToCancel;
    @Getter private final String ticker;
}
