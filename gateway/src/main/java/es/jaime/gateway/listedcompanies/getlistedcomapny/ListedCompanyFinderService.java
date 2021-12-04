package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.application.UseCase;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.springframework.stereotype.Service;

@Service
public class ListedCompanyFinderService extends UseCase {
    private final ListedCompaniesRepository repository;

    public ListedCompanyFinderService(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    public ListedCompany find(ListedCompanyTicker ticker){
        return repository.findByTicker(ticker)
                .orElseThrow(() -> new ResourceNotFound("Company not listed"));
    }
}
