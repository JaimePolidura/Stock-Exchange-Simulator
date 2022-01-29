package es.jaime.gateway.orders.pendingorder.execution.sell._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.OrderPositionIdToSell;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Order;
import es.jaimetruman.select.Select;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class SellOrderRepositoryMySQL extends DataBaseRepositoryValueObjects<SellOrder, OrderId> implements SellOrderRepostiry {
    protected SellOrderRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    public void save(SellOrder order) {
        super.save(order);
    }

    @Override
    public Optional<SellOrder> findByOrderId(OrderId id) {
        return super.findById(id);
    }

    @Override
    public List<SellOrder> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return buildListFromQuery(
                Select.from("sell_orders").where("clientId").equal(clientId.value()).and("state").equal(state.value())
        );
    }

    @Override
    public Optional<SellOrder> findLastOrderByStateAndByTicker(OrderState orderState, OrderTicker orderTicker) {
        return buildObjectFromQuery(
                Select.from("sell_orders").where("state").equal(orderState.value())
                        .and("ticker").equal(orderTicker.value())
                        .orderBy("date", Order.DESC)
                        .limit(1)
        );
    }

    @Override
    @SneakyThrows
    public void deleteByOrderId(OrderId orderId) {
        super.deleteById(orderId);
    }

    @Override
    public List<SellOrder> findByTicker(OrderTicker orderTicker) {
        return buildListFromQuery(
                Select.from("sell_orders").where("ticker").equal(orderTicker.value())
        );
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
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

    @Override
    protected Map<String, Object> toPrimitives(SellOrder sellOrder) {
        return sellOrder.toPrimitives();
    }
}
