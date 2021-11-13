package es.jaimetruman.exchangedistribuitor._shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public final class RawOrder {
    @Getter private final LocalDateTime time;
    @Getter private final UUID clientId;
    @Getter private final String ticker;
    @Getter private final int quantity;
    @Getter private final OrderType orderType;
    @Getter private final OrderExecutionType orderExecutionType;
}
