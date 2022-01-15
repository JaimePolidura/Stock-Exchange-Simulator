package es.jaime.gateway.orders.positions.closed.getclosedpositions;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GetClosedPositionsController extends Controller {
    private final QueryBus queryBus;

    public GetClosedPositionsController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/positions/closed/get")
    public ResponseEntity<GetClosedPositionsQueryResponse> getClosedPositions(){
        GetClosedPositionsQueryResponse response = queryBus.ask(new GetClosedPositionsQuery(
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }
}
