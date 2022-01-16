package es.jaime.gateway._shared.infrastrocture.exchanges;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class AllExchangesStarted extends DomainEvent {
    @Getter private Map<ListedCompany, String> listedCompanies;

    @Override
    public AllExchangesStarted fromPrimitives(Map<String, Object> primitives) {
        //Not needed
        return new AllExchangesStarted();
    }
}
