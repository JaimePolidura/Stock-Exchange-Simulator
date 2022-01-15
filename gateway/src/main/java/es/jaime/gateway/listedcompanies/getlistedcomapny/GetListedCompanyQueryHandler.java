package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetListedCompanyQueryHandler implements QueryHandler<GetListedCompanyQuery, GetListedCompanyQueryResponse> {
    private final ListedCompanyFinderService finderService;

    @Override
    public GetListedCompanyQueryResponse handle(GetListedCompanyQuery query) {
        ListedCompany listedCompany = finderService.find(query.getTicker());

        return new GetListedCompanyQueryResponse(
                listedCompany.ticker(),
                listedCompany.name()
        );
    }
}
