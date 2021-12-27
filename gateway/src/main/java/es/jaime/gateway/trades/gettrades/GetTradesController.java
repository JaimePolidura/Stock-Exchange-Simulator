package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GetTradesController extends Controller {
    private final QueryBus queryBus;

    public GetTradesController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/trades/get")
    public ResponseEntity<GetTradesQueryResponse> getTrades(){
        GetTradesQueryResponse response = queryBus.ask(new GetTradesQuery(
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }
}
