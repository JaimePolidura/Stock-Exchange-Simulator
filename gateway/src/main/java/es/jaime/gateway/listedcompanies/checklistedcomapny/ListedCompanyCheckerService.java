package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.application.UseCase;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompnayTicker;
import org.springframework.stereotype.Service;

@Service
public final class ListedCompanyCheckerService extends UseCase {
    private final Listed repository;

    public ListedCompanyCheckerService(ListedCompaniesRepositoryJPA repository) {
        this.repository = repository;
    }

    public boolean isListedCompany(String ticker){
        return repository.existsByTicker(ListedCompnayTicker.of(ticker));
    }
}
