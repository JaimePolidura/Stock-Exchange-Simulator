package es.jaime.gateway.listedcompanies.checklistedcomapny;

import es.jaime.gateway._shared.domain.bus.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public final class CheckListedCompanyController extends Controller {
    private final QueryBus queryBus;

    public CheckListedCompanyController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/checklistedcompany/{ticker}")
    public ResponseEntity<Boolean> checkListedCompany (@PathVariable String ticker){
        Boolean isListed = queryBus.ask(new CheckListedCompanyQuery(ticker));

        return buildNewHttpResponseOK(isListed);
    }
}
