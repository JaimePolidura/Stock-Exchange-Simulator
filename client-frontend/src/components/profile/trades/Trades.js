import TradeDisplayInTable from "./TradeDisplayInTable";
import React from "react";
import './Trades.css';

export default class Trades extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            trades: props.trades,
            onOrderSellSended: props.onOrderSellSended,
            listedCompanies: props.listedCompanies,
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
                        <TradeDisplayInTable key={trade.tradeId}
                                             listedCompany={this.getListedCompany(trade.ticker)}
                                             onOrderSellSended={order => this.onOrderSellSended(order)}
                                             value={trade}/>)
                    }
                </tbody>
            </table>
        );
   }
    
    getListedCompany(ticker){
        let found = this.state.listedCompanies.find(listedCompany => listedCompany.ticker == ticker);

        return found == null || false ?
            "Loading" :
            found;
    }

    onOrderSellSended(order){
        this.state.onOrderSellSended(order);
    }
}
