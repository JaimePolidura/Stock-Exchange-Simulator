package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.query.QueryBus;
import es.jaime.gateway._shared.infrastrocture.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public final class LoginController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public LoginController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/login")
    public ResponseEntity<GenerateTokenUserQueryResponse> login (@RequestBody Request request) {
        commandBus.dispatch(new AuthenticateCommand(
                request.username,
                request.password
        ));

        GenerateTokenUserQueryResponse response = queryBus.ask(new GenerateTokenUserQuery(
                request.username
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    private static class Request {
        public final String username;
        public final String password;
    }
}
