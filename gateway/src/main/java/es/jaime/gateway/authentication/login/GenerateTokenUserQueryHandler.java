package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.bus.query.QueryHandler;
import es.jaime.gateway.authentication._shared.infrastrocture.JWTUtils;
import org.springframework.stereotype.Service;


@Service
public class GenerateTokenUserQueryHandler implements QueryHandler<GenerateTokenUserQuery, GenerateTokenUserQueryResponse> {
    @Override
    public GenerateTokenUserQueryResponse handle(GenerateTokenUserQuery query) {
        String generatedToken = JWTUtils.generateToken(query.getUsername().value());

        return new GenerateTokenUserQueryResponse(generatedToken);
    }
}
