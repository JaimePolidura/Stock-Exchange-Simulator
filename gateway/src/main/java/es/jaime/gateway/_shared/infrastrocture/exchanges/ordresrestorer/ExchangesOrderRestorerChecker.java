package es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway._shared.infrastrocture.exchanges.AllExchangesStarted;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderFinder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Service
@AllArgsConstructor
public class ExchangesOrderRestorerChecker implements Runnable{
    private static final int INITIAL_TIME = 15000;
    private static final int DELAY_BEETWEEN_CHECK = 1000;

    private Map<ListedCompany, String> exchangesContainers;
    private final Executor executor;
    private final PendingOrderFinder orderFinder;
    private final MessagePublisher messagePublisher;
    private final DockerClient dockerClient;

    @EventListener({AllExchangesStarted.class})
    public void on(AllExchangesStarted event){
        this.exchangesContainers = new HashMap<>(event.getListedCompanies());

        this.executor.execute(this);
    }

    @Override
    @SneakyThrows
    public void run() {
        Thread.sleep(INITIAL_TIME);

        while(true){
            for(Map.Entry<ListedCompany, String> entry : this.exchangesContainers.entrySet()){
                ListedCompany listedCompany = entry.getKey();
                String containerId = entry.getValue();
                List<Container> allContainers = this.dockerClient.listContainersCmd().exec();

                Container exchangeContainer = findContainerById(allContainers, entry.getValue());

                if(notRunning(exchangeContainer)){
                    restoreData(listedCompany);
                }
            }

            Thread.sleep(DELAY_BEETWEEN_CHECK);
        }
    }

    private void restoreData(ListedCompany listedCompany){
        this.executor.execute(new ExchangeOrderRestorerService(orderFinder, messagePublisher, listedCompany));
    }

    private Container findContainerById(List<Container> containers, String id){
        return containers.stream()
                .filter(container -> container.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Contianer not found for ID: " + id));
    }

    private boolean notRunning(Container container){
        return !container.getState().equalsIgnoreCase("running");
    }
}
