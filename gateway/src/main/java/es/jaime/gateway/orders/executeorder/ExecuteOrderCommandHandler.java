package es.jaime.gateway.orders.executeorder;

import es.jaime.gateway._shared.domain.bus.command.CommandHandler;
import es.jaime.gateway._shared.domain.bus.event.EventBus;
import es.jaime.gateway.orders._shared.domain.ActiveOrder;
import es.jaime.gateway.orders._shared.domain.ActiveOrderRepository;
import es.jaime.gateway._shared.domain.bus.queue.QueuePublisher;

public final class ExecuteOrderCommandHandler implements CommandHandler<ExecuteOrderCommand> {
    private final EventBus eventBus;
    private final QueuePublisher queuePublisher;
    private final ActiveOrderRepository repository;

    public ExecuteOrderCommandHandler(EventBus eventBus, QueuePublisher queuePublisher, ActiveOrderRepository repository) {
        this.eventBus = eventBus;
        this.queuePublisher = queuePublisher;
        this.repository = repository;
    }

    @Override
    public void handle(ExecuteOrderCommand command) {
        this.queuePublisher.enqueue(command);

        this.eventBus.publish(new OrderExecutionPublished(command));

        this.repository.save(new ActiveOrder(
                command.getOrderID(),
                command.getClientID(),
                command.getDate(),
                command.getQuantity(),
                command.getTicker(),
                command.getType(),
                command.getExecutionPrice()
        ));
    }
}
