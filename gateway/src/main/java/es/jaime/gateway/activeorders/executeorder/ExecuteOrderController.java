package es.jaime.gateway.activeorders.executeorder;

import es.jaime.gateway._shared.domain.bus.command.CommandBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
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

    public ExecuteOrderController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/executeorder")
    public ResponseEntity<ActiveOrderID> executeorder(@RequestBody ExecuteOrderRequest request){
        //Order-id generated in commandExecute order
        ExecuteOrderCommand commandExecuteOrder = new ExecuteOrderCommand(
                getUsername(),
                request.quantity,
                request.ticker,
                request.orderType,
                request.executionType
        );

        this.commandBus.dispatch(commandExecuteOrder);

        return buildNewHttpResponseOK(commandExecuteOrder.getOrderID());
    }


    @AllArgsConstructor
    private static final class ExecuteOrderRequest{
        public final String clientId;
        public final String ticker;
        public final int quantity;
        public final String orderType;
        public final double executionType;
    }
}
