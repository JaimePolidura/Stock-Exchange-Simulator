import React, {Component} from 'react';
import OrderDisplayInTable from "./OrderDisplayInTable";

export default class Orders extends React.Component<any, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            orders: props.orders,
            listedCompanies: props.listedCompanies,
        }
    }

    render(): any {
        if(this.state.orders.length == 0){
            return null;
        }

        return (
            <table className="display-table">
                <thead>
                <tr>
                    <th>Ticker</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Quantity</th>
                    <th>Total value</th>
                    <th>Date</th>
                    <th>Execution</th>
                </tr>
                </thead>

                <tbody>
                    {this.state.orders.map((order: any) =>
                         <OrderDisplayInTable key={order.orderId}
                                              listedCompany={this.getListedCompanyFromTicker(order.ticker)}
                                              value={order}/>)}
                </tbody>
            </table>
        );
    }

    getListedCompanyFromTicker(ticker: string): any{
        return this.state.listedCompanies.find((ite: any) => ite.ticker == ticker);
    }
}
