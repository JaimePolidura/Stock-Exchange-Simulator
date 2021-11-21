package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.application.UseCase;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.springframework.stereotype.Service;

@Service
public class ListedCompanyCheckerService extends UseCase {
    private final ListedCompaniesRepository repository;

    public ListedCompanyCheckerService(ListedCompaniesRepository repository) {
        this.repository = repository;
    }


    public boolean isListedCompany(String ticker){
        return repository.existsByTicker(ListedCompanyTicker.of(ticker));
    }
}
