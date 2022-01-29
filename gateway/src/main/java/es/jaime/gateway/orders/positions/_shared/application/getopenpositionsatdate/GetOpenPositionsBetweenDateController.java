package es.jaime.gateway.orders.positions._shared.application.getopenpositionsatdate;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@AllArgsConstructor
public class GetOpenPositionsBetweenDateController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/orders/positionsbetweendate")
    public ResponseEntity<List<Map<String, Object>>> positionsAtDate(@RequestParam String from, @RequestParam String to){
        GetOpenPositionsBetweenDateQueryResponse queryResponse = this.queryBus.ask(new GetOpenPositionsBetweenDateQuery(
                getLoggedUsername(),
                from,
                to
        ));

        return buildNewHttpResponseOK(queryResponse.toPrimitives());
    }
}
