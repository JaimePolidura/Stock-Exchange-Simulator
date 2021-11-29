package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class CheckListedCompanyQueryResponse implements QueryResponse {
    @Getter private final Boolean isListed;
}
