package es.jaime.gateway.trades.gettrades;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.trades._shared.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GetTradesQueryResponse implements QueryResponse {
    @Getter private final List<GetTradeQueryResponse> trades;

    public static GetTradesQueryResponse create(List<Trade> trades){
        Map<String, GetTradeQueryResponse> toReturn = new HashMap<>();

        for(var trade : trades) {
            String ticker = trade.ticker().value();

            if(toReturn.containsKey(ticker))
                updateGetTradeQueryResposne(toReturn.get(ticker), trade);
            else
                toReturn.put(ticker, createNewGetTradeQueryResposne(trade));
        }

        return new GetTradesQueryResponse(new ArrayList<>(toReturn.values()));
    }

    private static void updateGetTradeQueryResposne(GetTradeQueryResponse toUpdate, Trade trade){
        int newQuantity = toUpdate.quantity + trade.quantity().value();
        double newAveragePrice = Math.round( ((toUpdate.averagePrice * toUpdate.quantity) +
                (trade.openingPrice().value() * trade.quantity().value()) ) / newQuantity);

        toUpdate.setAveragePrice(newAveragePrice);
        toUpdate.setQuantity(newQuantity);
    }

    private static GetTradeQueryResponse createNewGetTradeQueryResposne(Trade trade){
        return new GetTradeQueryResponse(
                trade.ticker().value(),
                trade.openingDate().value(),
                trade.openingPrice().value(),
                trade.quantity().value()
        );
    }

    @AllArgsConstructor
    private static class GetTradeQueryResponse {
        @Getter private final String ticker;
        @Getter private final String openingDate;
        @Getter @Setter private double averagePrice;
        @Getter @Setter private int quantity;
    }
}
