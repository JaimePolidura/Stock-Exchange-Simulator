package es.jaime.gateway.orders.pendingorder.execution._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder._shared.infrastructure.PendingOrderRepositoryMySQL;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderRepository;
import es.jaimetruman.select.Select;
import java.util.List;

public abstract class ExecutionOrderRepositoryMySQL<T extends ExecutionOrder> extends PendingOrderRepositoryMySQL<T>
        implements ExecutionOrderRepository<T> {

    public ExecutionOrderRepositoryMySQL(DatabaseConfiguration databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public List<T> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        String table = entityMapper().getTable();

        return buildListFromQuery(
                Select.from(table).where("clientId").equal(clientId.value()).and("state").equal(state.value())
        );
    }

    @Override
    public void deleteByOrderId(OrderId orderId) {
        super.deleteById(orderId);
    }
}
