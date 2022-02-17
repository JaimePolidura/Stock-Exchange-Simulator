package es.jaime.gateway._shared.infrastrocture.exchanges.starter.kubernetes;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.Utils;
import es.jaime.gateway._shared.infrastrocture.orchestration.kubernetes.KubernetesPodService;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static es.jaime.gateway._shared.domain.Utils.*;
import static java.lang.String.format;

@Component
@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@Order(51)
@AllArgsConstructor
public class ExchangesStarterKubernetes implements CommandLineRunner {
    private final ListedCompaniesRepository listedCompanies;
    private final ApplicationConfiguration configuration;
    private final ExchangePodBuilder exchangePodBuilder;
    private final KubernetesPodService kubernetesPodService;

    @SneakyThrows
    @Override
    public void run(String... args) {
        Executors.newCachedThreadPool().submit(() -> {
            this.deleteAlreadyCreatedPods();
            this.createAllPods();
        });
    }

    @SneakyThrows
    private void createAllPods(){
        List<String> tickersOfExchangesToStart = getTickersOfAllListedCompanies();
        int replicasPerExchange = configuration.getInt("EXCHANGE_REPLICAS");

        for (String tickerOfExchange : tickersOfExchangesToStart) {
            repeat(replicasPerExchange, () -> createPod(tickerOfExchange));
        }
    }

    private void createPod(String ticker){
        String exchangeName = nameForExchange(ticker);

        V1Pod pod = exchangePodBuilder.build(ticker, environmentVariablesForExchange(ticker, exchangeName), exchangeName);

        this.kubernetesPodService.createPod(pod);
    }

    private List<String> getTickersOfAllListedCompanies(){
        return listedCompanies.findAll().stream()
                .map(l -> l.ticker().value())
                .collect(Collectors.toList());
    }

    private Map<String, Object> environmentVariablesForExchange(String ticker, String exchangeName){
        return Map.of(
                "NEW_ORDERS_QUEUE_NAME", RabbitMQNameFormatter.newOrdersQueueName(ticker),
                "EVENTS_EXCHANGE", RabbitMQNameFormatter.EVENTS_EXCHANGE,
                "EXCHANGE_DELAY_BETWEEN_CHECK", configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                "TICKER", ticker,
                "EXCHANGE_INITIAL_DELAY", configuration.get("EXCHANGE_INITIAL_DELAY"),
                "EVENTS_ROUTING_KEY", RabbitMQNameFormatter.EVENTS_ROUTING_KEY,
                "EXCHANGE_NAME", exchangeName,
                "REDIS_HOST", configuration.get("REDIS_HOST"),
                "REDIS_PORT", configuration.get("REDIS_PORT"),
                "REDIS_PASSWORD", configuration.get("REDIS_PASSWORD")
        );
    }

    @SneakyThrows
    private void deleteAlreadyCreatedPods(){
        List<String> exchangeNames = this.getAllExchangesNames();

        for (String exchangeName : exchangeNames) {
            try {
                this.kubernetesPodService.deletePodByName(exchangeName);
            } catch (ApiException e) {
                //Not exists
            }
        }

        sleep(10000);
    }

    private List<String> getAllExchangesNames() throws ApiException {
        return this.kubernetesPodService.listPods().stream()
                .filter(pod -> kubernetesPodService.sameImage(pod, configuration.get("DOCKER_EXCHANGE_IMAGE")))
                .map(pod -> pod.getSpec().getContainers().get(0).getName())
                .collect(Collectors.toList());
    }

    public static String nameForExchange(String ticker){
        return format("exchange-%s-%s", ticker.toLowerCase(), firstPartOfUUID());
    }

    private static String firstPartOfUUID(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
