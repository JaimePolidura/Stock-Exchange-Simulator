package es.jaime.gateway.orders.execution.sell.send;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.cancel.getorder.GetCancelOrderQuery;
import es.jaime.gateway.orders.cancel.getorder.GetCancelOrderQueryResponse;
import es.jaime.gateway.orders.execution.sell.getorder.GetSellOrderQuery;
import es.jaime.gateway.orders.execution.sell.getorder.GetSellOrderQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SellOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public SellOrderController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/orders/sell/send")
    public ResponseEntity<GetSellOrderQueryResponse> sendOrderSell(@RequestBody Request request){
        //Order-id generated in commandExecute order
        SellOrderCommand sellOrderCommand = new SellOrderCommand(
                getLoggedUsername(),
                request.positionId,
                request.executionPrice,
                request.quantity
        );
        this.commandBus.dispatch(sellOrderCommand);

        GetSellOrderQueryResponse orderAdded = queryBus.ask(new GetSellOrderQuery(
                sellOrderCommand.getOrderID().value(),
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(orderAdded);
    }

    @AllArgsConstructor
    private static final class Request {
        public final String positionId;
        public final int quantity;
        public final double executionPrice;
    }
}
