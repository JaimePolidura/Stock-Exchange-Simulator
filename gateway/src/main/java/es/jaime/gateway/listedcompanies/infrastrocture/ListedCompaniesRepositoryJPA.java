package es.jaime.gateway.listedcompanies.infrastrocture;

import es.jaime.gateway.listedcompanies.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies.domain.ListedCompany;
import org.springframework.data.jpa.repository.JpaRepository;

//Si no funciona, hacer overrido del metodo de existsByTicker aqui
public interface ListedCompaniesRepositoryJPA extends ListedCompaniesRepository, JpaRepository<ListedCompany, String> {
}
