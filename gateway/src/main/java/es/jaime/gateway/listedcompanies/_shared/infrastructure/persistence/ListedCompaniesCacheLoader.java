package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public final class ListedCompaniesCacheLoader implements CommandLineRunner {
    private final ListedCompaniesRepository repository;

    public ListedCompaniesCacheLoader(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * This method already adds all items in cache
         */
        repository.findAll();
    }
}
