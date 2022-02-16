package es.jaime.gateway._shared.infrastrocture.exchanges.starter.kubernetes;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.domain.Utils;
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
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
@ConditionalOnProperty(value = "orchestration", havingValue = "kubernetes")
@Order(51)
@AllArgsConstructor
public class ExchangesStarterKubernetes implements CommandLineRunner {
    private final ListedCompaniesRepository listedCompanies;
    private final ApplicationConfiguration configuration;
    private final ExchangePodBuilder exchangePodBuilder;
    private final CoreV1Api kubernetesApiClient;

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

        for (String tickerOfExchange : tickersOfExchangesToStart) {
            V1Pod pod = exchangePodBuilder.build(tickerOfExchange, environmentVariablesForExchange(tickerOfExchange));

            kubernetesApiClient.createNamespacedPod("default", pod, null, null, null);
        }
    }

    private List<String> getTickersOfAllListedCompanies(){
        return listedCompanies.findAll().stream()
                .map(l -> l.ticker().value())
                .collect(Collectors.toList());
    }

    private Map<String, Object> environmentVariablesForExchange(String ticker){
        return Map.of(
                "NEW_ORDERS_QUEUE_NAME", RabbitMQNameFormatter.newOrdersQueueName(ticker),
                "EVENTS_EXCHANGE", RabbitMQNameFormatter.EVENTS_EXCHANGE,
                "EXCHANGE_DELAY_BETWEEN_CHECK", configuration.get("EXCHANGE_DELAY_BETWEEN_CHECK"),
                "TICKER", ticker,
                "EXCHANGE_INITIAL_DELAY", configuration.get("EXCHANGE_INITIAL_DELAY"),
                "EVENTS_ROUTING_KEY", RabbitMQNameFormatter.EVENTS_ROUTING_KEY,
                "EXCHANGE_NAME", "exchange-" + ticker,
                "REDIS_HOST", configuration.get("REDIS_HOST"),
                "REDIS_PORT", configuration.get("REDIS_PORT"),
                "REDIS_PASSWORD", configuration.get("REDIS_PASSWORD")
        );
    }

    private void deleteAlreadyCreatedPods(){
        List<String> exchangeNames = this.getAllExchangeNames();

        for (String exchangeName : exchangeNames) {
            try {
                this.kubernetesApiClient.deleteNamespacedPod(exchangeName, "default", null, null, null, null, null, null);
            } catch (ApiException e) {
                //Not exists
            }
        }

        Utils.sleep(10000);
    }

    private List<String> getAllExchangeNames(){
        return this.getTickersOfAllListedCompanies().stream()
                .map(ExchangesStarterKubernetes::nameForExchange)
                .collect(Collectors.toList());
    }

    public static String nameForExchange(String ticker){
        return format("exchange-%s", ticker.toLowerCase());
    }
}
