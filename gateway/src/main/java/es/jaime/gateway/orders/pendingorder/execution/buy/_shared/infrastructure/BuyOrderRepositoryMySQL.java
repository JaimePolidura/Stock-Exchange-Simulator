package es.jaime.gateway.orders.pendingorder.execution.buy._shared.infrastructure;

import es.jaime.connection.DatabaseConnection;
import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Order;
import es.jaimetruman.select.Select;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class BuyOrderRepositoryMySQL extends DataBaseRepositoryValueObjects<BuyOrder, OrderId> implements BuyOrderRepostory {
    protected BuyOrderRepositoryMySQL(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void save(BuyOrder order) {
        super.save(order);
    }

    @Override
    public Optional<BuyOrder> findByOrderId(OrderId id) {
        return super.findById(id);
    }

    @Override
    public List<BuyOrder> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return buildListFromQuery(
                Select.from("buy_orders").where("clientId").equal(clientId.value()).and("state").equal(state.value())
        );
    }

    @Override
    public Optional<BuyOrder> findLastOrderByStateAndByTicker(OrderState orderState, OrderTicker orderTicker) {
        return buildObjectFromQuery(
                Select.from("buy_orders").where("state").equal(orderState.value())
                        .and("ticker").equal(orderTicker.value())
                        .orderBy("date", Order.DESC).limit(1)
        );
    }

    @Override
    public void deleteByOrderId(OrderId orderId) {
        super.deleteById(orderId);
    }

    @Override
    public List<BuyOrder> findByTicker(OrderTicker orderTicker) {
        return buildListFromQuery(
                Select.from("buy_orders").where("ticker").equal(orderTicker.value())
        );
    }

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return StringValueObject::value;
    }

    @Override
    protected Map<String, Object> toValueObjects(BuyOrder buyOrder) {
        return Map.of(
                "orderId", buyOrder.getOrderId(),
                "clientId", buyOrder.getClientId(),
                "date", buyOrder.getDate(),
                "state", buyOrder.getState(),
                "ticker", buyOrder.getTicker(),
                "pendingOrderType", buyOrder.getPendingOrderType(),
                "quantity", buyOrder.getQuantity(),
                "priceToExecute", buyOrder.getPriceToExecute()
        );
    }

    @Override
    protected EntityMapper<BuyOrder> entityMapper() {
        return EntityMapper
                .table("buy_orders")
                .idField("orderId")
                .classToMap(BuyOrder.class)
                .build();
    }

    @Override
    public BuyOrder buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new BuyOrder(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                PendingOrderType.of(resultSet.getString("pendingOrderType")),
                OrderQuantity.of(resultSet.getInt("quantity")),
                OrderPriceToExecute.of(resultSet.getDouble("priceToExecute"))
        );
    }

    @Override
    protected Map<String, Object> toPrimitives(BuyOrder buyOrder) {
        return buyOrder.toPrimitives();
    }
}
