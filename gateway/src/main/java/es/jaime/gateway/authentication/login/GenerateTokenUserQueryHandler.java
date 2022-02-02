package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.infrastructure.JWTUtils;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethodsIf;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GenerateTokenUserQueryHandler implements QueryHandler<GenerateTokenUserQuery, GenerateTokenUserQueryResponse> {
    private final JWTUtils jwtUtils;

    @Override
    public GenerateTokenUserQueryResponse handle(GenerateTokenUserQuery query) {
        String generatedToken = jwtUtils.generateToken(query.getUsername().value());

        return new GenerateTokenUserQueryResponse(generatedToken);
    }
}
