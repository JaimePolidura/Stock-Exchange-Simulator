package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GetActiveOrdersController extends Controller {
    private final QueryBus queryBus;

    public GetActiveOrdersController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/getorders")
    public ResponseEntity<GetActiveOrdersQuery> getorders(){
        GetActiveOrdersQuery response = queryBus.ask(new GetActiveOrdersQuery(getLoggedUsername()));

        return buildNewHttpResponseOK(response);
    }
}
