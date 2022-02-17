package es.jaime.gateway._shared.infrastrocture.exchanges;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.exchange.ExchangeContainerApiService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static es.jaime.gateway._shared.domain.Utils.repeat;
import static es.jaime.gateway._shared.domain.Utils.sleep;

@Service
public class ExchangeStarter implements CommandLineRunner {
    private final ExchangeContainerApiService containerService;
    private final ApplicationConfiguration configuration;
    private final ListedCompanyFinderService listedCompanyFinderService;

    public ExchangeStarter(@Qualifier("containerservice-kubernetes") ExchangeContainerApiService cs, ApplicationConfiguration c,
                           ListedCompanyFinderService lcf) {
        this.containerService = cs;
        this.configuration = c;
        this.listedCompanyFinderService = lcf;
    }

    @Override
    public void run(String... args) {
        Executors.newCachedThreadPool().submit(() -> {
            this.deleteAlreadyCreatedPods();
            this.createAllContainers();
        });
    }

    private void deleteAlreadyCreatedPods() {
        List<String> containers = this.containerService.findContainerIdentifiersNamesByImage(configuration.get("DOCKER_EXCHANGE_IMAGE"));

        for (String container : containers) {
            try{
                this.containerService.deleteContainerByIdentifier(container);
            }catch (Exception e){
                //Ignore bcs it might not exists
            }
        }

        sleep(10000);
    }

    private void createAllContainers() {
        List<String> tickersOfExchangesToStart = this.getTickersOfListedCompanies();
        int replicasPerExchange = configuration.getInt("EXCHANGE_REPLICAS");

        for (String tickerOfExchange : tickersOfExchangesToStart) {
            repeat(replicasPerExchange, () -> createContainer(tickerOfExchange));
        }
    }

    private void createContainer(String tickerOfExchange) {
        this.containerService.startContainer(tickerOfExchange);
    }

    private List<String> getTickersOfListedCompanies( ){
        return this.listedCompanyFinderService.all().stream()
                .map(l -> l.ticker().value())
                .collect(Collectors.toList());
    }
}
