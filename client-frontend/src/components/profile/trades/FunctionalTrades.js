import React, {useEffect, useState} from "react";

const FunctionalTrades = props => {
    const [trades, setTrades] = useState(props.trades);

    useEffect(() => {
        console.log(trades);
    }, []);

    return (
        <table class="display-table">
            <thead>
            <tr>
                <th>Ticker</th>
                <th>Name</th>
                <th>Opening Price</th>
                <th>Opening date</th>
                <th>Actual price</th>
                <th>Quantity</th>
                <th>Market value</th>
                <th>Result</th>
                <th>Result %</th>
            </tr>
            </thead>

            <tbody>
            {trades.map(trade => {
                console.log(trade);

                // <TradeDisplayInTable key={trade.tradeId} value={trade}/>
            })}
            </tbody>
        </table>
    );
}

export default FunctionalTrades;