package es.jaime.gateway._shared.infrastrocture.exchanges;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@Order(51)
@AllArgsConstructor
public class ExchangesStarterKubernetes implements CommandLineRunner {
    private final ListedCompaniesRepository listedCompanies;
    private final ApplicationConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {

    }
}
