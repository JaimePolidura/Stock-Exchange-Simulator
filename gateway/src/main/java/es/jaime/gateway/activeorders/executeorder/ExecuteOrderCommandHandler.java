package es.jaime.gateway.activeorders.executeorder;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway._shared.domain.queue.QueuePublisher;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderStatus;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import org.springframework.stereotype.Service;

@Service
public class ExecuteOrderCommandHandler implements CommandHandler<ExecuteOrderCommand> {
    private final EventBus eventBus;
    private final QueuePublisher queuePublisher;
    private final ActiveOrderRepository repository;
    private final ListedCompanyFinderService listedCompanyFinder;

    public ExecuteOrderCommandHandler(EventBus eventBus, QueuePublisher queuePublisher, ActiveOrderRepository repository,
                                      ListedCompanyFinderService listedCompanyFinder) {
        this.eventBus = eventBus;
        this.queuePublisher = queuePublisher;
        this.repository = repository;
        this.listedCompanyFinder = listedCompanyFinder;
    }

    @Override
    public void handle(ExecuteOrderCommand command) {
        ensureTickerExists(command);
        ensureCorrectQuantity(command);

        this.eventBus.publish(new OrderExecutionPublished(command));

        this.repository.save(new ActiveOrder(
                command.getOrderID(),
                command.getTicker(),
                command.getClientID(),
                command.getDate(),
                command.getQuantity(),
                command.getType(),
                command.getExecutionPrice(),
                ActiveOrderStatus.of(ActiveOrderStatus.Status.SENDING)
        ));

        this.queuePublisher.enqueue(command);
    }

    private void ensureTickerExists(ExecuteOrderCommand command){
        //If it is not found the service will throw ResourceNotFound exception
        listedCompanyFinder.find(ListedCompanyTicker.of(command.getTicker().value()));
    }

    private void ensureCorrectQuantity(ExecuteOrderCommand command){
        if(command.getQuantity().value() <= 0){
            throw new IllegalQuantity("Quantity cannot be smaller or equal to 0");
        }
    }
}
