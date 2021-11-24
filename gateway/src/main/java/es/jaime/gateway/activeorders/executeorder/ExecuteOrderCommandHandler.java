package es.jaime.gateway.activeorders.executeorder;

import es.jaime.gateway._shared.domain.bus.command.CommandHandler;
import es.jaime.gateway._shared.domain.bus.event.EventBus;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQConfiguration;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway._shared.domain.bus.queue.QueuePublisher;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderStatus;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.listedcompanies.checklistedcomapny.ListedCompanyCheckerService;
import net.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.stereotype.Service;

@Service
public class ExecuteOrderCommandHandler implements CommandHandler<ExecuteOrderCommand> {
    private final EventBus eventBus;
    private final QueuePublisher queuePublisher;
    private final ActiveOrderRepository repository;
    private final ListedCompanyCheckerService listedCompanyCheckerService;

    public ExecuteOrderCommandHandler(EventBus eventBus, QueuePublisher queuePublisher, ActiveOrderRepository repository,
                                      ListedCompanyCheckerService listedCompanyCheckerService) {
        this.eventBus = eventBus;
        this.queuePublisher = queuePublisher;
        this.repository = repository;
        this.listedCompanyCheckerService = listedCompanyCheckerService;
    }

    @Override
    public void handle(ExecuteOrderCommand command) {
        ensureTickerExists(command);
        ensureCorrectQuantity(command);

        this.queuePublisher.enqueue(RabbitMQConfiguration.newOrders, command);

        this.eventBus.publish(new OrderExecutionPublished(command));

        this.repository.save(new ActiveOrder(
                command.getOrderID(),
                command.getClientID(),
                command.getDate(),
                command.getQuantity(),
                command.getTicker(),
                command.getType(),
                command.getExecutionPrice(),
                ActiveOrderStatus.of(ActiveOrderStatus.Status.SENDING)
        ));
    }

    private void ensureTickerExists(ExecuteOrderCommand command){
        if(!listedCompanyCheckerService.isListedCompany(ListedCompanyTicker.of(command.getTicker()))){
            throw new ResourceNotFound("Stock not listed");
        }
    }

    private void ensureCorrectQuantity(ExecuteOrderCommand command){
        if(command.getQuantity().value() <= 0){
            throw new IllegalQuantity("Quantity cannot be smaller or equal to 0");
        }
    }
}
