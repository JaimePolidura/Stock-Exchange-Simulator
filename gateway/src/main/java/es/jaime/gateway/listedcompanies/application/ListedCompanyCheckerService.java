package es.jaime.gateway.listedcompanies.application;

import es.jaime.gateway._shared.application.UseCase;
import es.jaime.gateway.listedcompanies.domain.ListedCompnayTicker;
import es.jaime.gateway.listedcompanies.infrastrocture.ListedCompaniesRepositoryJPA;
import org.springframework.stereotype.Service;

@Service
public final class ListedCompanyCheckerService extends UseCase {
    private final ListedCompaniesRepositoryJPA repository;

    public ListedCompanyCheckerService(ListedCompaniesRepositoryJPA repository) {
        this.repository = repository;
    }

    public boolean isListedCompany(String ticker){
        return repository.existsByTicker(ListedCompnayTicker.of(ticker));
    }
}
