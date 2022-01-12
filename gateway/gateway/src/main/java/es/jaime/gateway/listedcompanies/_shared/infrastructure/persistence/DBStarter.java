package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This is needed to start the data base
 */
@Component
@AllArgsConstructor
public class DBStarter implements CommandLineRunner {
    private final ListedCompaniesRepository repository;

    @Override
    public void run(String... args) throws Exception {
        try {
            repository.findAll();
        }catch (Exception e){
            //IGNORE
        }
    }
}
