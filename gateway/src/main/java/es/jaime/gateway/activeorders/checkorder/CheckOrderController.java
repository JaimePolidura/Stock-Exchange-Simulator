package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public final class CheckOrderController extends Controller {
    private final QueryBus queryBus;

    public CheckOrderController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/checkorder}")
    public ResponseEntity<CheckOrderQueryResponse> checkorder(@RequestParam Request request){
        CheckOrderQueryResponse response = this.queryBus.ask(new CheckOrderQuery(
                request.activeOrderId,
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    private static final class Request {
        public final String username;
        public final String activeOrderId;
    }
}
