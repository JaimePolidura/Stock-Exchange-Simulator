package es.jaime.gateway.orders.getorders;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GetOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/getorders")
    public ResponseEntity<GetOrdersQueryResponse> getorders(){
        GetOrdersQueryResponse response = queryBus.ask(new GetOrdersQuery(getLoggedUsername()));
        
        return buildNewHttpResponseOK(response);
    }
}
