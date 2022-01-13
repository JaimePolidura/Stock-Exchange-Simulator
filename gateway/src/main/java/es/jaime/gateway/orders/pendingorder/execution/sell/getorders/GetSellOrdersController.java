package es.jaime.gateway.orders.pendingorder.execution.sell.getorders;

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
public class GetSellOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetSellOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/orders/sell/get")
    public ResponseEntity<GetSellOrdersResponse> getOrdersByType(@RequestParam List<String> orderStates){
        GetSellOrdersResponse response = queryBus.ask(new GetSellOrdersQuery(
                getLoggedUsername(),
                orderStates
        ));

        return buildNewHttpResponseOK(response);
    }
}
