package es.jaime.gateway.authentication.validtoken;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.authentication._shared.infrastructure.JWTUtils;
import org.springframework.stereotype.Service;

@Service
public class IsValidTokenQueryHandler implements QueryHandler<IsValidTokenQuery, IsValidTokenQueryResponse> {
    @Override
    public IsValidTokenQueryResponse handle(IsValidTokenQuery query) {
        return new IsValidTokenQueryResponse(JWTUtils.isValid(query.getToken(), query.getUserName()));
    }
}
