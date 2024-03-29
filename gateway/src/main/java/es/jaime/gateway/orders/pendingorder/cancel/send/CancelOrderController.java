package es.jaime.gateway.orders.pendingorder.cancel.send;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.pendingorder.cancel.getorder.GetCancelOrderQuery;
import es.jaime.gateway.orders.pendingorder.cancel.getorder.GetCancelOrderQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class CancelOrderController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/orders/cancel/send/{orderIdToCancel}")
    public ResponseEntity<GetCancelOrderQueryResponse> sendCancelOrder(@PathVariable String orderIdToCancel){
        //Order-id generated in commandExecute order
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(
                orderIdToCancel,
                getLoggedUsername()
        );

        commandBus.dispatch(cancelOrderCommand);

        GetCancelOrderQueryResponse orderAdded = queryBus.ask(new GetCancelOrderQuery(
                cancelOrderCommand.getOrderId().value(),
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(orderAdded);
    }
}
