package es.jaime.gateway.listedcompanies._shared.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ListedCompaniesRepository {
    boolean existsByTicker(ListedCompanyTicker ticker);
}
