package es.jaime.gateway.listedcompanies.application;

import es.jaime.gateway._shared.application.UseCase;
import es.jaime.gateway.listedcompanies.domain.ListedCompaniesRepository;
import org.springframework.stereotype.Service;

@Service
public final class ListedCompanyCheckerService extends UseCase {
    private final ListedCompaniesRepository repository;

    public ListedCompanyCheckerService(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    public boolean isListedCompany(String ticker){
        return repository.existsByTicker(ticker);
    }
}
