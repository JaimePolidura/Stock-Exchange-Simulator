package es.jaime.gateway.orders.executeorder;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway._shared.domain.queue.QueuePublisher;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders._shared.domain.OrderStatus;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import org.springframework.stereotype.Service;

@Service
public class ExecuteOrderCommandHandler implements CommandHandler<ExecuteOrderCommand> {
    private final EventBus eventBus;
    private final QueuePublisher queuePublisher;
    private final OrdersRepository repository;
    private final ListedCompanyFinderService listedCompanyFinder;

    public ExecuteOrderCommandHandler(EventBus eventBus, QueuePublisher queuePublisher, OrdersRepository repository,
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

        this.repository.save(new Order(
                command.getOrderID(),
                command.getTicker(),
                command.getClientID(),
                command.getDate(),
                command.getQuantity(),
                command.getType(),
                command.getExecutionPrice(),
                OrderStatus.of(OrderStatus.Status.SENDING)
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
