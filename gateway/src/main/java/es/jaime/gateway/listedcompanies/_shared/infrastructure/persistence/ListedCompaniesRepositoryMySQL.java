package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.connection.DatabaseConnection;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyName;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ListedCompaniesRepositoryMySQL extends DataBaseRepositoryValueObjects<ListedCompany, ListedCompanyTicker> implements ListedCompaniesRepository {
    protected ListedCompaniesRepositoryMySQL(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker) {
        return super.findById(ticker);
    }

    @Override
    public List<ListedCompany> findAll() {
        return super.all();
    }

    @Override
    protected Function<ListedCompanyTicker, Object> idValueObjectToIdPrimitive() {
        return ListedCompanyTicker::value;
    }

    @Override
    protected Map<String, Object> toValueObjects(ListedCompany listedCompany) {
        return Map.of(
                "ticker", listedCompany.ticker(),
                "name", listedCompany.name()
        );
    }

    @Override
    protected EntityMapper<ListedCompany> entityMapper() {
        return EntityMapper
                .table("listedcompanies")
                .classToMap(ListedCompany.class)
                .idField("ticker")
                .build();
    }

    @Override
    public ListedCompany buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new ListedCompany(ListedCompanyTicker.of(resultSet.getString("ticker")), ListedCompanyName.of(resultSet.getString("name")));
    }

    @Override
    protected Map<String, Object> toPrimitives(ListedCompany listedCompany) {
        return Map.of(
                "ticker", listedCompany.ticker().value(),
                "name", listedCompany.name().value()
        );
    }
}
