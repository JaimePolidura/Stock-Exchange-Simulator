package es.jaime.gateway.orders.positions._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.positions._shared.domain.Position;
import es.jaime.gateway.orders.positions._shared.domain.PositionRepository;
import es.jaime.repository.DataBaseRepositoryValueObjects;

import java.util.Map;
import java.util.function.Function;

public abstract class PositionsRepositoryMySQL<T extends Position> extends DataBaseRepositoryValueObjects<T, OrderId> implements PositionRepository<T> {
    protected PositionsRepositoryMySQL(DatabaseConfiguration databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(T position) {
        super.save(position);
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
