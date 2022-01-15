package es.jaime.gateway.listedcompanies.getlalllistedcompanies;

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
public class GetAllListedCompaniesController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/listedcompanies/get")
    public ResponseEntity<GetAllListedCompaniesQueryResponse> getAllListedCompanies(){
        GetAllListedCompaniesQueryResponse response = queryBus.ask(new GetAllListedCompaniesQuery());

        return ResponseEntity.ok(response);
    }
}
