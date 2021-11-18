package es.jaime.gateway.listedcompanies.infrastrocture;

import es.jaime.gateway.listedcompanies.domain.ListedCompany;
import es.jaime.gateway.listedcompanies.domain.ListedCompnayTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Si no funciona, hacer overrido del metodo de existsByTicker aqui
@Repository
public interface ListedCompaniesRepositoryJPA extends JpaRepository<ListedCompany, ListedCompnayTicker> {
    Boolean existsByTicker(ListedCompnayTicker ticker);
}
