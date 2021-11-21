package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.springframework.stereotype.Repository;

@Repository
public class ListedCompanyRepositoryMySQL implements ListedCompaniesRepository {
    @Override
    public boolean existsByTicker(ListedCompanyTicker ticker) {
        return false;
    }
}
