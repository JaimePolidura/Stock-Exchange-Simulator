package es.jaime.gateway.listedcompanies.getlalllistedcompanies;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GetAllListedCompaniesController extends Controller {
    private final QueryBus queryBus;

    public GetAllListedCompaniesController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/getalllistedcompanies")
    public ResponseEntity<GetAllListedCompaniesQueryResponse> getAllListedCompanies(){
        GetAllListedCompaniesQueryResponse response = queryBus.ask(new GetAllListedCompaniesQuery());

        return ResponseEntity.ok(response);
    }
}
