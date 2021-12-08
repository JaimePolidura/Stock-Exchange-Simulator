package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public final class GetOrderController extends Controller {
    private final QueryBus queryBus;

    public GetOrderController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/getorder/{orderId}")
    public ResponseEntity<GetOrderQueryResponse> getOrder(@PathVariable String orderId){
        GetOrderQueryResponse response = this.queryBus.ask(new GetOrderQuery(
                orderId,
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }
}
