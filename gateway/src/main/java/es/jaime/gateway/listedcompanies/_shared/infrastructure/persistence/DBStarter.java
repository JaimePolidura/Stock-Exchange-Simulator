package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This is needed to start the data base
 */
@Component
@AllArgsConstructor
public class DBStarter implements ApplicationRunner {
    private final ListedCompaniesRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            repository.findAll();
        }catch (Exception e){
            //IGNORE
        }
    }
}
