package es.jaime.gateway.orders.pendingorder.execution.sell.send;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.pendingorder.execution._shared.application.getorder.GetExecutionOrderQuery;
import es.jaime.gateway.orders.pendingorder.execution._shared.application.getorder.GetExecutionOrderQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@AllArgsConstructor
public class SellOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/orders/sell/send")
    public ResponseEntity<Resposne> sendOrderSell(@RequestBody Request request){
        //Order-id generated in commandExecute order
        SellOrderCommand sellOrderCommand = new SellOrderCommand(
                getLoggedUsername(),
                request.positionId,
                request.priceToExecute,
                request.quantity
        );
        this.commandBus.dispatch(sellOrderCommand);

        GetExecutionOrderQueryResponse orderAdded = queryBus.ask(new GetExecutionOrderQuery(
                sellOrderCommand.getOrderID().value(),
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(new Resposne(orderAdded));
    }

    private static class Resposne {
        public final Map<String, Object> order;

        public Resposne(GetExecutionOrderQueryResponse queryResponse){
            this.order = queryResponse.toPrimitives();
        }
    }
    
    @AllArgsConstructor
    private static final class Request {
        public final String positionId;
        public final int quantity;
        public final double priceToExecute;
    }
}
