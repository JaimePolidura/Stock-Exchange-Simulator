package es.jaime.gateway.activeorders.executeorder;

import es.jaime.gateway._shared.domain.bus.command.CommandBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public final class ExecuteOrderController extends Controller {
    private final CommandBus commandBus;

    public ExecuteOrderController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/executeorder")
    public ResponseEntity<String> executeorder(@RequestBody ExecuteOrderRequest request){
        this.commandBus.dispatch(new ExecuteOrderCommand(
                request.clientId,
                request.quantity,
                request.ticker,
                request.orderType,
                request.executionType
        ));

        return buildNewHttpResponseOK("Order pending to execute");
    }


}
