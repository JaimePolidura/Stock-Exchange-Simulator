import TradeDisplayInTable from "./openpositionsdisplayintable/OpenPositionDisplayInTable";
import React, {ReactElement} from "react";
import {ListedCompany} from "../../../model/listedcomapnies/ListedCompany";
import {OpenPosition} from "../../../model/positions/OpenPosition";
import {SellOrder} from "../../../model/orders/SellOrder";

export default class OpenPositions extends React.Component<OpenPositoinsProps, OpenPositionsState> {
    constructor(props: OpenPositoinsProps) {
        super(props);

        this.state = {
            trades: props.trades,
            onOrderSellSended: props.onOrderSellSended,
            listedCompanies: props.listedCompanies,
        };
    }

    render(): ReactElement {
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
                    {this.state.trades.map((trade: OpenPosition) =>
                        // @ts-ignore
                        <TradeDisplayInTable key={trade.orderId}
                                             listedCompany={this.getListedCompany(trade.ticker)}
                                             onOrderSellSended={(order: any) => this.onOrderSellSended(order)}
                                             value={trade}/>)
                    }
                </tbody>
            </table>
        );
   }

   getListedCompany(ticker: string): ListedCompany {
       // @ts-ignore
       return this.state.listedCompanies.find((listedCompany: ListedCompany) => listedCompany.ticker == ticker);
   }

   onOrderSellSended(order: SellOrder): void{
       this.state.onOrderSellSended(order);
   }
}

export interface OpenPositionsState {
    trades: OpenPosition[],
    onOrderSellSended: (order: SellOrder) => void,
    listedCompanies: ListedCompany[],
}

export interface OpenPositoinsProps {
    trades: OpenPosition[],
    onOrderSellSended: (order: SellOrder) => void,
    listedCompanies: ListedCompany[],
}
