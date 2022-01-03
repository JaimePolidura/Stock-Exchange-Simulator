import React, {Component} from 'react';
import OrderDisplayInTable from "./OrderDisplayInTable";

export default class Orders extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            orders: props.orders,
            listedCompanies: props.listedCompanies,
        }
    }

    render() {
        if(this.state.orders.length == 0){
            return null;
        }

        return (
            <table class="display-table">
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
                    {this.state.orders.map(order =>
                         <OrderDisplayInTable key={order.orderId}
                                              listedCompany={this.getListedCompanyFromTicker(order.body.ticker)}
                                              value={order}/>)}
                </tbody>
            </table>
        );
    }

    getListedCompanyFromTicker(ticker){
        return this.state.listedCompanies.find(ite => ite.ticker == ticker);
    }
}
