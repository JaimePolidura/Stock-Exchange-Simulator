package es.jaime.gateway._shared.infrastrocture.test.largedbtest;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators.*;
import es.jaimetruman.insert.Insert;
import es.jaimetruman.insert.InsertOptionFinal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@AllArgsConstructor
public class ClosedPositionsQueryInserter {
    private final DatabaseConfiguration databaseConfiguration;

    private final ApplicationConfiguration applicationConfiguration;
    private final RandomUserGenerator userGenerator;
    private final RandomDateGenerator dateGenerator;
    private final RandomPriceGenerator doubleGenerator;
    private final RandomListedCompanyGenerator listedCompanyGenerator;
    private final RandomUUIDGenerator uuidGenerator;
    private final RandomQuantityGenerator quantityGenerator;

    public void insertAll(){
        final int openPositionsPerUser = applicationConfiguration.getInt("CLOSED_POSITIONS_PER_USER");
        final int maxUsers = applicationConfiguration.getInt("NUMBER_OF_USERS");
        InsertOptionFinal insert = Insert.table("closed_positions")
                .fields("orderId", "clientId", "date", "state", "ticker", "quantity", "executedOrderType",
                        "openingPrice", "openingDate", "closingPrice", "closingDate");

        for(int i = 0; i < maxUsers; i++){
            for (int j = 0; j < openPositionsPerUser; j++) {
                try {
                    String query = insert.values(uuidGenerator.get(), userGenerator.get(), dateGenerator.get(), "EXECUTED",
                            listedCompanyGenerator.get(), quantityGenerator.get(100), "CLOSED", doubleGenerator.get(1000), dateGenerator.get(),
                            doubleGenerator.get(10000), dateGenerator.get());

                    databaseConfiguration.sendUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
