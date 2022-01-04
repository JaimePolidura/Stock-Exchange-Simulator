package es.jaime.gateway.orders.orders.sendorder.cancel;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.orders.getorderid.GetOrderQuery;
import es.jaime.gateway.orders.orders.getorderid.GetOrderQueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class CancelOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public CancelOrderController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/orders/send/cancel/{orderIdToCancel}")
    public ResponseEntity<GetOrderQueryResponse> sendCancelOrder(@PathVariable String orderIdToCancel){
        //Order-id generated in commandExecute order
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(
                orderIdToCancel,
                getLoggedUsername()
        );

        commandBus.dispatch(cancelOrderCommand);

        GetOrderQueryResponse orderAdded = queryBus.ask(new GetOrderQuery(
                cancelOrderCommand.getOrderId().value(),
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(orderAdded);
    }
}
