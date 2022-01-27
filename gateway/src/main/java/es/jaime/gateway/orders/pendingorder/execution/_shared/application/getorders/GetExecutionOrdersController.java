package es.jaime.gateway.orders.pendingorder.execution._shared.application.getorders;

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
public class GetExecutionOrdersController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/orders/execution/get")
    public ResponseEntity<Response> getOrdersByType(@RequestParam List<String> orderStates){
        GetExecutionOrdersQueryResponse queryResponse = queryBus.ask(new GetExecutionOrdersQuery(
                getLoggedUsername(),
                orderStates
        ));

        return buildNewHttpResponseOK(new Response(queryResponse));
    }

    private static class Response {
        public final List<Map<String, Object>> orders;

        public Response(GetExecutionOrdersQueryResponse queryResponse) {
            this.orders = queryResponse.toPrimitives();
        }
    }
}
