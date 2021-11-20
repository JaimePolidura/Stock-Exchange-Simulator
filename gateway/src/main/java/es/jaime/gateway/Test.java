package es.jaime.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    private ListedCompaniesRepositoryJPA repositoryJPA;

    public Test(ListedCompaniesRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(repositoryJPA.existsByTicker(ListedCompnayTicker.of("V")));
    }
}
