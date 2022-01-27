package es.jaime.gateway.orders.pendingorder.execution.buy.send;

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
public class BuyOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/orders/buy/send")
    public ResponseEntity<Response> sendOrderBuy(@RequestBody Request request){
        BuyOrderCommand buyOrderCommand = new BuyOrderCommand(
                getLoggedUsername(),
                request.ticker,
                request.priceToExecute,
                request.quantity
        );

        this.commandBus.dispatch(buyOrderCommand);

        //Order-id generated in commandExecute order
        GetExecutionOrderQueryResponse orderAdded = queryBus.ask(new GetExecutionOrderQuery(
                buyOrderCommand.getOrderID().value(),
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(new Response(orderAdded));
    }

    @AllArgsConstructor
    private static final class Request {
        public final String ticker;
        public final int quantity;
        public final double priceToExecute;
    }

    private static class Response{
        public final Map<String, Object> order;

        public Response(GetExecutionOrderQueryResponse queryResponse){
            this.order = queryResponse.toPrimitives();
        }
    }
}
