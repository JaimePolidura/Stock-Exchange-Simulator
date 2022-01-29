package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import es.jaime.gateway.orders.positions.open.getopenpositions.GetOpenPositionsQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class GetOpenPositionsAtDateController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/orders/positionsatdate")
    public ResponseEntity<GetOpenPositionsQueryResponse> positionsAtDate(@RequestParam Request request){
        GetOpenPositionsQueryResponse queryResponse = this.queryBus.ask(new GetOpenPositionsAtDateQuery(
                request.from,
                request.to
        ));

        return buildNewHttpResponseOK(queryResponse);
    }

    @AllArgsConstructor
    private static class Request {
        public final String from;
        public final String to;
    }
}
