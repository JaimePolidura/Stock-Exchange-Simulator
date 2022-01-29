package es.jaime.gateway.orders.positions.closed._shared.infrastrucutre.persistance;

import es.jaime.connection.DatabaseConnection;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingPrice;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionRepository;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Select;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Repository
public class ClosedPositionRepositoryMySQL extends DataBaseRepositoryValueObjects<ClosedPosition, OrderId> implements ClosedPositionRepository {
    private static final String TABLE = "closed_positions";

    protected ClosedPositionRepositoryMySQL(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(ClosedPosition closedPosition) {
        super.save(closedPosition);
    }

    @Override
    public List<ClosedPosition> findByClientId(OrderClientId clientId) {
        return buildListFromQuery(
                Select.from(TABLE).where("clientId").equal(clientId.value())
        );
    }

    @Override
    public List<ClosedPosition> findBetweenDate(PositionOpeningDate openingDate, ClosedPositionClosingDate closingDate) {
        return buildListFromQuery(
                Select.from(TABLE).where("openingDate").smallerOrEqual(openingDate.value())
                        .and("closingDate").biggerOrEqual(closingDate.value())
        );
    }

    @Override
    protected EntityMapper<ClosedPosition> entityMapper() {
        return EntityMapper
                .table(TABLE)
                .idField("orderId")
                .classToMap(ClosedPosition.class)
                .build();
    }

    @Override
    public ClosedPosition buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new ClosedPosition(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                OrderQuantity.of(resultSet.getInt("quantity")),
                ExecutedOrderType.of(resultSet.getString("executedOrderType")),
                PositionOpeningPrice.of(resultSet.getDouble("openingPrice")),
                PositionOpeningDate.of(resultSet.getString("openingDate")),
                ClosedPositionClosingPrice.of(resultSet.getDouble("closingPrice")),
                ClosedPositionClosingDate.of(resultSet.getString("closingDate"))
        );
    }

    @Override
    protected Map<String, Object> toPrimitives(ClosedPosition closedPosition) {
        return new HashMap<>() {{
            put("orderId", closedPosition.getOrderId().value());
            put("clientId", closedPosition.getClientId().value());
            put("date", closedPosition.getDate().value());
            put("state", closedPosition.getState().value());
            put("ticker", closedPosition.getTicker().value());
            put("quantity", closedPosition.getQuantity().value());
            put("executedOrderType", closedPosition.getExecutedOrderType().value());
            put("openingPrice", closedPosition.getOpeningPrice().value());
            put("openingDate", closedPosition.getOpeningDate().value());
            put("closingPrice", closedPosition.getClosingPrice().value());
            put("closingDate", closedPosition.getClosingDate().value());
        }};
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
    }

    @Override
    protected Map<String, Object> toValueObjects(ClosedPosition closedPosition) {
        return new HashMap<>() {{
            put("orderId", closedPosition.getOrderId());
            put("clientId", closedPosition.getClientId());
            put("date", closedPosition.getDate());
            put("state", closedPosition.getState());
            put("ticker", closedPosition.getTicker());
            put("quantity", closedPosition.getQuantity());
            put("executedOrderType", closedPosition.getExecutedOrderType());
            put("openingPrice", closedPosition.getOpeningPrice());
            put("openingDate", closedPosition.getOpeningDate());
            put("closingPrice", closedPosition.getClosingPrice());
            put("closingDate", closedPosition.getClosingDate());
        }};
    }
}
