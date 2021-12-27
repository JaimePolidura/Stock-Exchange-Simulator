package es.jaime.gateway.orders.executeorder;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.checkorder.GetOrderQuery;
import es.jaime.gateway.orders.checkorder.GetOrderQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ExecuteOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public ExecuteOrderController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/orders/execute")
    public ResponseEntity<GetOrderQueryResponse> executeorder(@RequestBody Request request){
        //Order-id generated in commandExecute order
        ExecuteOrderCommand commandExecuteOrder = new ExecuteOrderCommand(
                getLoggedUsername(),
                request.quantity,
                request.ticker,
                request.orderType,
                request.executionType
        );
        this.commandBus.dispatch(commandExecuteOrder);

        GetOrderQueryResponse response = this.queryBus.ask(new GetOrderQuery(
                commandExecuteOrder.getOrderID().getOrderID(),
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    private static final class Request {
        public final String ticker;
        public final int quantity;
        public final String orderType;
        public final double executionType;
    }

}
