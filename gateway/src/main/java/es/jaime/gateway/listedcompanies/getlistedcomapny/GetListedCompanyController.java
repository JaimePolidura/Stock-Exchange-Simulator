package es.jaime.gateway.listedcompanies.getlistedcomapny;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@CrossOrigin
public final class GetListedCompanyController extends Controller {
    private final QueryBus queryBus;

    public GetListedCompanyController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/listedcompanies/get/{ticker}")
    public ResponseEntity<Response> getListedCompany (@PathVariable String ticker){
        GetListedCompanyQueryResponse queryResponse = queryBus.ask(new GetListedCompanyQuery(ticker));

        return buildNewHttpResponseOK(new Response(queryResponse));
    }

    private static class Response implements Serializable {
        @Getter public final String ticker;
        @Getter public final String name;

        public Response(GetListedCompanyQueryResponse queryResponse){
            this.ticker = queryResponse.getTicker().value();
            this.name = queryResponse.getName().value();
        }
    }
}
