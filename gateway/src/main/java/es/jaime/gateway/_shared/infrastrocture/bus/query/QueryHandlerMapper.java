package es.jaime.gateway._shared.infrastrocture.bus.query;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.infrastrocture.bus.ApplicationCQRSClassMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryHandlerMapper {
    private final Map<Class<Query>, QueryHandler> queryHandlers;

    public QueryHandlerMapper(ApplicationCQRSClassMapper mapper) {
        queryHandlers = mapper.find(QueryHandler.class);
    }

    public QueryHandler search(Class<? extends Query> queryClass) {
        return queryHandlers.get(queryClass);
    }
}
