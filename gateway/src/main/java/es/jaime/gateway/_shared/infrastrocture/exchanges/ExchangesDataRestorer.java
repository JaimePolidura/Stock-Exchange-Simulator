package es.jaime.gateway._shared.infrastrocture.exchanges;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
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
public class ExchangesDataRestorer implements Runnable{
    private static final int INITIAL_TIME = 10000;
    private static final int DELAY_BEETWEEN_CHECK = 500;

    private Map<ListedCompany, String> exchangesContainers;
    private final Executor executor;
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
                    this.executor.execute(() -> this.restoreData(listedCompany));
                }
            }

            Thread.sleep(INITIAL_TIME);
        }
    }

    private void restoreData(ListedCompany listedCompany){
        //TODO
    }

    private Container findContainerById(List<Container> containers, String id){
        return containers.stream()
                .filter(container -> container.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Contianer not found for ID: " + id));
    }

    private boolean notRunning(Container container){
        return container.getState().equalsIgnoreCase("running");
    }
}
