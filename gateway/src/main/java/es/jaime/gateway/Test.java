package es.jaime.gateway;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    private ListedCompaniesRepository repository;

    public Test(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(repository.existsByTicker(ListedCompanyTicker.of("V")));
    }
}
