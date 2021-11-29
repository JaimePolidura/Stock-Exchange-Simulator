package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.Query;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import lombok.Getter;

public final class CheckListedCompanyQuery implements Query {
    @Getter private final ListedCompanyTicker ticker;

    public CheckListedCompanyQuery(String ticker){
        this.ticker = ListedCompanyTicker.of(ticker);
    }
}
