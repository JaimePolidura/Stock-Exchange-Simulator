package es.jaime.gateway._shared.infrastrocture.bus.query;

import es.jaime.gateway._shared.domain.bus.query.Query;
import es.jaime.gateway._shared.domain.bus.query.QueryHandler;
import es.jaime.gateway._shared.infrastrocture.bus.ApplicationCQRSClassMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryHandlerMapper {
    private final Map<Class<Query>, QueryHandler> queryHandlers;
    private final ApplicationCQRSClassMapper mapper;
    private final ApplicationContext context;

    public QueryHandlerMapper(ApplicationCQRSClassMapper mapper, ApplicationContext context) {
        this.mapper = mapper;
        this.context = context;

        queryHandlers = mapper.find(QueryHandler.class);
    }

    public QueryHandler search(Class<? extends Query> queryClass) {
        return queryHandlers.get(queryClass);
    }
}
