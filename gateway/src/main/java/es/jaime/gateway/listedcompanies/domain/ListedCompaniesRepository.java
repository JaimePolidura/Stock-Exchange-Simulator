package es.jaime.gateway.listedcompanies.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ListedCompaniesRepository {
    Boolean existsByTicker(String ticker);
}
