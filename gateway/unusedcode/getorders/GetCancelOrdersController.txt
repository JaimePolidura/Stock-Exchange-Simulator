package es.jaime.gateway.orders.pendingorder.cancel.getorders;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GetCancelOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetCancelOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/orders/cancel/get")
    public ResponseEntity<GetCancelOrdersResponse> getOrdersByType(@RequestParam List<String> orderStates){
        GetCancelOrdersResponse response = queryBus.ask(new GetCancelOrdersQuery(
                getLoggedUsername(),
                orderStates
        ));

        return buildNewHttpResponseOK(response);
    }
}
