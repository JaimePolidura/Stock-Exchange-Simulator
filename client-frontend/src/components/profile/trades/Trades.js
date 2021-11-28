import TradeDisplayInTable from "./TradeDisplayInTable";
import React from "react";
import './Trades.css';

export default class Trades extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
            trades: props.trades,
        };
    }

    render() {
        return (
            <table class="display-trades-table">
                <thead>
                <tr>
                    <th>Ticker</th>
                    <th>Name</th>
                    <th>Average Price</th>
                    <th>Actual price</th>
                    <th>Quantity</th>
                    <th>Market value</th>
                    <th>Result</th>
                    <th>Result %</th>
                </tr>
                </thead>

                <tbody>
                    {this.state.trades.map(trade => <TradeDisplayInTable key={trade.name} value={trade}/>)}
                </tbody>
            </table>
        );
   }
}
