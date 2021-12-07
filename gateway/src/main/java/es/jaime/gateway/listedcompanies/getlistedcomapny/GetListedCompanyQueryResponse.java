package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.QueryResponse;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyCurrencyCode;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyCurrencySymbol;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyName;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetListedCompanyQueryResponse implements QueryResponse {
    @Getter private final ListedCompanyTicker ticker;
    @Getter private final ListedCompanyName name;
    @Getter private final ListedCompanyCurrencyCode currencyCode;
    @Getter private final ListedCompanyCurrencySymbol currencySymbol;
}
