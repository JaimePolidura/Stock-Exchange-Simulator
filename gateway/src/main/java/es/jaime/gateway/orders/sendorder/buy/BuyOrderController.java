package es.jaime.gateway.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders._shared.domain.OrderTypeValues;
import es.jaime.gateway.orders.getorder.GetOrderQuery;
import es.jaime.gateway.orders.getorder.GetOrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    @PostMapping("/orders/send/buy")
    public ResponseEntity<Response> sendOrderBuy(@RequestBody Request request){
        //Order-id generated in commandExecute order
        BuyOrderCommand buyOrderCommand = new BuyOrderCommand(
                getLoggedUsername(),
                request.ticker,
                request.executionPrice,
                request.quantity
        );
        this.commandBus.dispatch(buyOrderCommand);

        Response response = buildResponseFromRequestAndCommnad(request, buyOrderCommand);

        return buildNewHttpResponseOK(response);
    }

    private Response buildResponseFromRequestAndCommnad(Request request, BuyOrderCommand command){
        return new Response(
                command.getOrderID().value(),
                request.ticker,
                OrderTypeValues.BUY.toString(),
                request.executionPrice,
                request.quantity,
                command.getOrderDate().value()
        );
    }

    @AllArgsConstructor
    private static final class Response{
        public final String orderId;
        public final String ticker;
        public final String type;
        public final double executionPrice;
        public final int quantity;
        public final String date;
    }

    @AllArgsConstructor
    private static final class Request {
        public final String ticker;
        public final int quantity;
        public final double executionPrice;
    }
}
