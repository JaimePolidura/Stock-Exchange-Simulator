package es.jaime.gateway.orders.positions.open._shared.infrastructure;

import es.jaime.connection.DatabaseConnection;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Select;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class OpenPositionsRepositoryMySQL extends DataBaseRepositoryValueObjects<OpenPosition, OrderId> implements OpenPositionsRepository {
    protected OpenPositionsRepositoryMySQL(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(OpenPosition openPosition) {
        super.save(openPosition);
    }

    @Override
    public List<OpenPosition> findByClientId(OrderClientId clientId) {
        return buildListFromQuery(Select.from("open_positions").where("clientId").equal(clientId.value()));
    }

    @Override
    public Optional<OpenPosition> findByPositionId(OrderId positionId) {
        return super.findById(positionId);
    }

    @Override
    public void deleteByPositionId(OrderId positionId) {
        super.deleteById(positionId);
    }

    @Override
    protected EntityMapper<OpenPosition> entityMapper() {
        return EntityMapper.table("open_positions")
                .classToMap(OpenPosition.class)
                .idField("orderId")
                .build();
    }

    @Override
    public OpenPosition buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new OpenPosition(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                OrderQuantity.of(resultSet.getInt("quantity")),
                ExecutedOrderType.of(resultSet.getString("executedOrderType")),
                PositionOpeningPrice.of(resultSet.getDouble("openingPrice")),
                PositionOpeningDate.of(resultSet.getString("openingDate"))
        );
    }

    @Override
    protected Map<String, Object> toPrimitives(OpenPosition openPosition) {
        return new HashMap<>() {{
            put("orderId", openPosition.getOrderId().value());
            put("clientId", openPosition.getClientId().value());
            put("date", openPosition.getDate().value());
            put("state", openPosition.getState().value());
            put("ticker", openPosition.getTicker().value());
            put("quantity", openPosition.getQuantity().value());
            put("executedOrderType", openPosition.getExecutedOrderType().value());
            put("openingPrice", openPosition.getOpeningPrice().value());
            put("openingDate", openPosition.getOpeningDate().value());
        }};
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
    }

    @Override
    protected Map<String, Object> toValueObjects(OpenPosition openposition) {
        return new HashMap<>() {{
            put("orderId", openposition.getOrderId());
            put("clientId", openposition.getClientId());
            put("date", openposition.getDate());
            put("state", openposition.getState());
            put("ticker", openposition.getTicker());
            put("quantity", openposition.getQuantity());
            put("executedOrderType", openposition.getExecutedOrderType());
            put("openingPrice", openposition.getOpeningPrice());
            put("openingDate", openposition.getOpeningDate());
        }};
    }
}
