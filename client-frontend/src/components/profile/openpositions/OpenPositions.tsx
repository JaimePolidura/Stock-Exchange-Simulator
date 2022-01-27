import TradeDisplayInTable from "./OpenPositionDisplayInTable";
import React from "react";
import './OpenPositions.css';

export default class OpenPositions extends React.Component<any, any> {
    constructor(props: any) {
        super(props);

        console.log(props.trades);

        this.state = {
            trades: props.trades,
            onOrderSellSended: props.onOrderSellSended,
            listedCompanies: props.listedCompanies,
        };
    }

    render(): any {
        return (
            <table className="display-table">
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
                    {this.state.trades.map((trade: any) =>
                        <TradeDisplayInTable key={trade.positionId}
                                             listedCompany={this.getListedCompany(trade.ticker)}
                                             onOrderSellSended={(order: any) => this.onOrderSellSended(order)}
                                             value={trade}/>)
                    }
                </tbody>
            </table>
        );
   }
    
    getListedCompany(ticker: string): string | any{
        let found = this.state.listedCompanies.find((listedCompany: any) => listedCompany.ticker == ticker);

        return found == null || false ?
            "Loading" :
            found;
    }

    onOrderSellSended(order: any): void{
        this.state.onOrderSellSended(order);
    }
}
