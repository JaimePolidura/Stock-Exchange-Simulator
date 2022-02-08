package es.jaime.gateway.orders.pendingorder.execution.sell._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution._shared.infrastructure.ExecutionOrderRepositoryMySQL;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.OrderPositionIdToSell;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.mapper.EntityMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class SellOrderRepositoryMySQL extends ExecutionOrderRepositoryMySQL<SellOrder> implements SellOrderRepostiry {
    protected SellOrderRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    protected Map<String, Object> toValueObjects(SellOrder sellOrder) {
        return Map.of(
                "orderId", sellOrder.getOrderId(),
                "clientId", sellOrder.getClientId(),
                "date", sellOrder.getDate(),
                "state", sellOrder.getState(),
                "ticker", sellOrder.getTicker(),
                "pendingOrderType", sellOrder.getPendingOrderType(),
                "quantity", sellOrder.getQuantity(),
                "priceToExecute", sellOrder.getPriceToExecute(),
                "positionIdToSell", sellOrder.getPositionIdToSell()
        );
    }

    @Override
    protected EntityMapper<SellOrder> entityMapper() {
        return EntityMapper
                .table("sell_orders")
                .idField("orderId")
                .classToMap(SellOrder.class)
                .build();
    }

    @Override
    public SellOrder buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new SellOrder(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                PendingOrderType.of(resultSet.getString("pendingOrderType")),
                OrderQuantity.of(resultSet.getInt("quantity")),
                OrderPriceToExecute.of(resultSet.getDouble("priceToExecute")),
                OrderPositionIdToSell.of(resultSet.getString("positionIdToSell"))
        );
    }
}
