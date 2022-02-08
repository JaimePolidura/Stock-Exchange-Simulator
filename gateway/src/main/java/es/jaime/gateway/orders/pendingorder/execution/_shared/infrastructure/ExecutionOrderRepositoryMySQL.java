package es.jaime.gateway.orders.pendingorder.execution._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderRepository;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Select;
import es.jaimetruman.select.SelectOptionFull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class ExecutionOrderRepositoryMySQL<T extends ExecutionOrder> extends DataBaseRepositoryValueObjects<T, OrderId>
        implements ExecutionOrderRepository<T> {

    public ExecutionOrderRepositoryMySQL(DatabaseConfiguration databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(T order) {
        super.save(order);
    }

    @Override
    public Optional<T> findByOrderId(OrderId id) {
        return super.findById(id);
    }

    @Override
    public List<T> findByOrdersId(List<OrderId> ordersId) {
        String table = entityMapper().getTable();
        SelectOptionFull selectQuery = Select.from(table)
                .where("orderId").equal(ordersId.get(0).value());

        if(ordersId.size() == 1) return buildListFromQuery(selectQuery);

        for (int i = 1; i < ordersId.size(); i++) {
            String orderId = ordersId.get(i).value();

            selectQuery = selectQuery.or("orderId").equal(orderId);
        }

        return buildListFromQuery(selectQuery);
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

    @Override
    public List<T> findByTicker(OrderTicker orderTicker) {
        return buildListFromQuery(Select.from(entityMapper().getTable()).where("ticker").equal(orderTicker.value()));
    }

    @Override
    protected Map<String, Object> toPrimitives(T t) {
        return t.toPrimitives();
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
    }
}
