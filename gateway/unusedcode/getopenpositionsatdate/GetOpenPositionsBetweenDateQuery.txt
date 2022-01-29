package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.exceptions.NotValid;
import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import lombok.Getter;

import java.time.LocalDateTime;

public final class GetOpenPositionsBetweenDateQuery implements Query {
    @Getter private final LocalDateTime from;
    @Getter private final LocalDateTime to;
    @Getter private final OrderClientId clientId;

    public GetOpenPositionsBetweenDateQuery(String clientId, String from, String to) {
        this.clientId = OrderClientId.of(clientId);
        this.from = LocalDateTime.parse(from);
        this.to = LocalDateTime.parse(to);

        this.checkIfRangeIsOk(this.from, this.to);
    }

    private void checkIfRangeIsOk(LocalDateTime from, LocalDateTime to){
        if(!from.isBefore(to))
            throw new NotValid("From cannot bo after to");
    }
}
