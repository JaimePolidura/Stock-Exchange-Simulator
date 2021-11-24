package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class CheckOrderQueryResponse implements QueryResponse {
    @Getter private final String status;
    @Getter private final int quantity;
}
