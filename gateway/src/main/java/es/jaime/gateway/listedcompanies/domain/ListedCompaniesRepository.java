package es.jaime.gateway.listedcompanies.domain;

public interface ListedCompaniesRepository {
    Boolean existsByTicker(String ticker);
}
