package es.jaime.gateway.authentication.validtoken;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.infrastructure.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IsValidTokenQueryHandler implements QueryHandler<IsValidTokenQuery, IsValidTokenQueryResponse> {
    private final JWTUtils jwtUtils;

    @Override
    public IsValidTokenQueryResponse handle(IsValidTokenQuery query) {
        return new IsValidTokenQueryResponse(jwtUtils.isValid(query.getToken(), query.getUserName()));
    }
}
