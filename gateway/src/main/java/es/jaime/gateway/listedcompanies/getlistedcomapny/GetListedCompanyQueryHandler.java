package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import org.springframework.stereotype.Service;

@Service
public final class GetListedCompanyQueryHandler implements QueryHandler<GetListedCompanyQuery, GetListedCompanyQueryResponse> {
    private final ListedCompanyFinderService finderService;

    public GetListedCompanyQueryHandler(ListedCompanyFinderService finderService) {
        this.finderService = finderService;
    }

    @Override
    public GetListedCompanyQueryResponse handle(GetListedCompanyQuery query) {
        ListedCompany listedCompany = finderService.find(query.getTicker());

        return new GetListedCompanyQueryResponse(
                listedCompany.ticker(),
                listedCompany.name(),
                listedCompany.currencyCode(),
                listedCompany.currencySymbol()
        );
    }
}
