package es.jaime.gateway.orders.positions._shared.application.getpositionsatdate;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import lombok.Getter;

import java.time.LocalDateTime;

public final class GetOpenPositionsAtDateQuery implements Query {
    @Getter private final PositionOpeningDate openingDate;
    @Getter private final OrderClientId clientId;

    public GetOpenPositionsAtDateQuery(String date, String clientId) {
        this.openingDate = PositionOpeningDate.of(LocalDateTime.parse(date));
        this.clientId = OrderClientId.of(clientId);
    }
}
