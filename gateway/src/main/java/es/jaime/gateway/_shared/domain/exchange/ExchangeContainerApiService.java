package es.jaime.gateway._shared.domain.exchange;

import io.kubernetes.client.openapi.ApiException;

import java.util.List;

public interface ExchangeContainerApiService {
    void deleteContainerByIdentifier(String identifier) throws ApiException;
    List<String> findContainerIdentifiersNamesByImage(String image);
    void startContainer(String ticker);
}
