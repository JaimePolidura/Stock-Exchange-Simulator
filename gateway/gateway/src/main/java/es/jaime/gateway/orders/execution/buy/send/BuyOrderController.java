package es.jaime.gateway.orders.execution.buy.send;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.cancel.getorder.GetCancelOrderQuery;
import es.jaime.gateway.orders.cancel.getorder.GetCancelOrderQueryResponse;
import es.jaime.gateway.orders.execution.buy.getorder.GetBuyOrderQuery;
import es.jaime.gateway.orders.execution.buy.getorder.GetBuyOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class BuyOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public BuyOrderController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/orders/buy/send")
    public ResponseEntity<GetBuyOrderQueryResponse> sendOrderBuy(@RequestBody Request request){
        BuyOrderCommand buyOrderCommand = new BuyOrderCommand(
                getLoggedUsername(),
                request.ticker,
                request.executionPrice,
                request.quantity
        );

        this.commandBus.dispatch(buyOrderCommand);

        //Order-id generated in commandExecute order
        GetBuyOrderQueryResponse response = queryBus.ask(new GetBuyOrderQuery(
                buyOrderCommand.getOrderID().value(),
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    @ToString
    private static final class Request {
        public final String ticker;
        public final int quantity;
        public final double executionPrice;
    }
}
