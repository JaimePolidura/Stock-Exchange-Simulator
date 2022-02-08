package es.jaime.gateway.orders.pendingorder._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrder;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderRepository;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Select;
import es.jaimetruman.select.SelectOptionFull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class PendingOrderRepositoryMySQL<T extends PendingOrder> extends DataBaseRepositoryValueObjects<T, OrderId>
        implements PendingOrderRepository<T> {

    protected PendingOrderRepositoryMySQL(DatabaseConfiguration databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(T order) {
        super.save(order);
    }

    @Override
    public Optional<T> findByOrderId(OrderId orderId) {
        return super.findById(orderId);
    }

    @Override
    public List<T> findByTicker(OrderTicker ticker) {
        var table = entityMapper().getTable();

        return buildListFromQuery(
                Select.from(table).where("ticker").equal(ticker.value())
        );
    }

    @Override
    public List<T> findByOrdersId(List<OrderId> ordersId) {
        var table = entityMapper().getTable();

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
    protected Map<String, Object> toPrimitives(T t) {
        return t.toPrimitives();
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
    }
}
