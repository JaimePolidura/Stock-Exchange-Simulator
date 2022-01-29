package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.exceptions.NotValid;
import es.jaime.gateway._shared.domain.query.Query;
import lombok.Getter;

import java.time.LocalDateTime;

public final class GetOpenPositionsAtDateQuery implements Query {
    @Getter private final LocalDateTime from;
    @Getter private final LocalDateTime to;

    public GetOpenPositionsAtDateQuery(String from, String to) {
        this.from = LocalDateTime.parse(from);
        this.to = LocalDateTime.parse(to);

        this.checkIfRangeIsOk(this.from, this.to);
    }

    private void checkIfRangeIsOk(LocalDateTime from, LocalDateTime to){
        if(!from.isBefore(to))
            throw new NotValid("From cannot bo after to");
    }
}
