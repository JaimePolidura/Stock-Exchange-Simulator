package es.jaime.gateway.orders.execution.sell.getorders;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class GetOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/orders/sell/get")
    public ResponseEntity<GetOrdersResponse> getOrdersByType(@RequestParam List<String> orderStates){
        GetOrdersResponse response = queryBus.ask(new GetOrdersQuery(
                getLoggedUsername(),
                orderStates
        ));

        return buildNewHttpResponseOK(response);
    }
}
