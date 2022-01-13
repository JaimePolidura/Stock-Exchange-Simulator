package es.jaime.gateway.orders.pendingorder.execution.buy.getorders;

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
public class GetBuyOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetBuyOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/orders/buy/get")
    public ResponseEntity<GetBuyOrdersResponse> getOrdersByType(@RequestParam List<String> orderStates){
        GetBuyOrdersResponse response = queryBus.ask(new GetBuyOrdersQuery(
                getLoggedUsername(),
                orderStates
        ));

        return buildNewHttpResponseOK(response);
    }
}
