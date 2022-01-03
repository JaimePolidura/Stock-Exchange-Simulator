package es.jaime.gateway.orders.orders.getorderstype;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GetOrdersByTypeController extends Controller {
    private final QueryBus queryBus;

    public GetOrdersByTypeController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/orders/get/type")
    public ResponseEntity<GetOrdersQueryResponse> getOrdersByType(@RequestParam List<Integer> orderTypesId){
        GetOrdersQueryResponse response = queryBus.ask(new GetOrdersByTypeQuery(getLoggedUsername(), orderTypesId));

        return buildNewHttpResponseOK(response);
    }
}
