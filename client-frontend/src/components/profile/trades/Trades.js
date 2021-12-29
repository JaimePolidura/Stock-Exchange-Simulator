import TradeDisplayInTable from "./TradeDisplayInTable";
import React from "react";
import './Trades.css';

export default class Trades extends React.Component {
    constructor(props) {
        super(props);

        console.log(props);

        this.state = {
            trades: props.trades,
            onOrderSellSended: props.onOrderSellSended,
        };
    }

    render() {
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
                    {this.state.trades.map(trade =>
                        <TradeDisplayInTable key={trade.tradeId} onOrderSellSended={order => this.onOrderSellSended(order)} value={trade}/>)
                    }
                </tbody>
            </table>
        );
   }

    onOrderSellSended(order){
        this.state.onOrderSellSended(order);
    }

}
