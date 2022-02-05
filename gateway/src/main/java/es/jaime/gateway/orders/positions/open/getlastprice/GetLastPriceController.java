package es.jaime.gateway.orders.positions.closed.getlastprice;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class GetLastPriceController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/orders/lastprice/{ticker}")
    public ResponseEntity<Double> getLastPrice(@PathVariable String ticker){
        GetLastPriceQueryResponse response = this.queryBus.ask(new GetLastPriceQuery(ticker));

        return buildNewHttpResponseOK(response.getLastPrice());
    }
}
