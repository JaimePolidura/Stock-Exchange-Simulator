package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomListedCompanyGenerator {
    private final List<ListedCompany> listedCompanies;

    public RandomListedCompanyGenerator(ListedCompaniesRepository listedCompanies) {
        this.listedCompanies = listedCompanies.findAll();
    }

    /**
     * @return ticker of the random listedcompany
     */
    public String get(){
        return listedCompanies.get((int) (Math.random() * listedCompanies.size()))
                .ticker()
                .value();
    }
}
