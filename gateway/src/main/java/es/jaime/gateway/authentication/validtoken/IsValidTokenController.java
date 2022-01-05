package es.jaime.gateway.authentication.validtoken;

import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.helpers.AbstractUnmarshallerImpl;

@RestController
@CrossOrigin
public class IsValidTokenController extends Controller {
    private final QueryBus queryBus;

    public IsValidTokenController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/auth/isvalidtoken")
    public ResponseEntity<Boolean> isValidToken(Request request){
        IsValidTokenQueryResponse response = queryBus.ask(new IsValidTokenQuery(request.username, request.token));

        return buildNewHttpResponseOK(response.getIsValid());
    }

    @AllArgsConstructor
    private static final class Request {
        public final String username;
        public final String token;
    }
}
