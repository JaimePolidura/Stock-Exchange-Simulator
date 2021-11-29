package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public final class CheckListedCompanyQueryHandler implements QueryHandler<CheckListedCompanyQuery, CheckListedCompanyQueryResponse> {
    private final ListedCompanyCheckerService checkerService;

    public CheckListedCompanyQueryHandler(ListedCompanyCheckerService checkerService) {
        this.checkerService = checkerService;
    }

    @Override
    public CheckListedCompanyQueryResponse handle(CheckListedCompanyQuery query) {
        Boolean isListed = checkerService.isListedCompany(query.getTicker());

        return new CheckListedCompanyQueryResponse(isListed);
    }
}
