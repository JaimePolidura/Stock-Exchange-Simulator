package es.jaime.gateway.orders.positions.open._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions._shared.infrastructure.PositionsRepositoryMySQL;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import es.jaime.mapper.EntityMapper;
import es.jaimetruman.select.Select;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Repository
public class OpenPositionsRepositoryMySQL extends PositionsRepositoryMySQL<OpenPosition> implements OpenPositionsRepository {
    private static final String TABLE = "open_positions";

    protected OpenPositionsRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    public List<OpenPosition> findByClientId(OrderClientId clientId) {
        return buildListFromQuery(Select.from(TABLE).where("clientId").equal(clientId.value()));
    }

    public List<OpenPosition> findOpenByClientIdAndSince(OrderClientId clientId, PositionOpeningDate date) {
        return buildListFromQuery(
                Select.from(TABLE).where("openingDate").smallerOrEqual(date.value())
                        .and("clientId").equal(clientId.value())
        );
    }

    @Override
    public Optional<OpenPosition> findByPositionId(OrderId positionId) {
        return super.findById(positionId);
    }

    @Override
    public Optional<OpenPosition> findLastOpenPositionsByTicker(OrderTicker ticker) {
        return buildObjectFromQuery(format("SELECT * FROM %s WHERE ticker = %s ORDER BY date DESC LIMIT 1", TABLE, ticker.value()));
    }

    @Override
    public void deleteByPositionId(OrderId positionId) {
        super.deleteById(positionId);
    }

    @Override
    protected EntityMapper<OpenPosition> entityMapper() {
        return EntityMapper.table(TABLE)
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
