package es.jaime.gateway.orders.positions._shared.application.getpositionsatdate;

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

@RestController
@CrossOrigin
@AllArgsConstructor
public class GetOpenPositionsAtDateController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/positions/openpositionsatdate")
    public ResponseEntity<List<Map<String, Object>>> getOpenPositionsAtDate(@RequestParam String date){
        GetOpenPositionsAtDateQueryResponse response = queryBus.ask(new GetOpenPositionsAtDateQuery(date, getLoggedUsername()));

        return buildNewHttpResponseOK(response.toPrimitives());
    }
}
