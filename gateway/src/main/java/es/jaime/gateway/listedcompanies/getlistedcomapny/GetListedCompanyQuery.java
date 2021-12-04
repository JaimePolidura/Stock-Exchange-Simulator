package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.Query;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import lombok.Getter;

public final class GetListedCompanyQuery implements Query {
    @Getter private final ListedCompanyTicker ticker;

    public GetListedCompanyQuery(String ticker){
        this.ticker = ListedCompanyTicker.of(ticker);
    }
}
