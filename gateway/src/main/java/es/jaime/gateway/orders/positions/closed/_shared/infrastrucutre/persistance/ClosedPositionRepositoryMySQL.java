package es.jaime.gateway.orders.positions.closed._shared.infrastrucutre.persistance;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions._shared.infrastructure.PositionsRepositoryMySQL;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingPrice;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionRepository;
import es.jaime.mapper.EntityMapper;
import es.jaimetruman.select.Select;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClosedPositionRepositoryMySQL extends PositionsRepositoryMySQL<ClosedPosition> implements ClosedPositionRepository {
    private static final String TABLE = "closed_positions";

    protected ClosedPositionRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    public List<ClosedPosition> findByClientIdAndOpeningClosingDateBetween(OrderClientId clientId, PositionOpeningDate dateToBeBetween) {
        return buildListFromQuery(
                Select.from(TABLE).where("clientId").equal(clientId.value())
                        .and("openingDate").smallerOrEqual(dateToBeBetween.value())
                        .and("closingDate").biggerOrEqual(dateToBeBetween.value())
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
