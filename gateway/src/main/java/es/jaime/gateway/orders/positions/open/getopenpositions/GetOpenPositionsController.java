package es.jaime.gateway.orders.positions.open.getopenpositions;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class GetOpenPositionsController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/positions/open/get")
    public ResponseEntity<GetOpenPositionsQueryResponse> getTrades(){
        GetOpenPositionsQueryResponse response = queryBus.ask(new GetOpenPositionsQuery(
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }
}
