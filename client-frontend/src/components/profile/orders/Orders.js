import React, {Component} from 'react';
import OrderDisplayInTable from "./OrderDisplayInTable";

export default class Orders extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            orders: props.orders
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
                    <th>Status</th>
                    <th>Execution</th>
                </tr>
                </thead>

                <tbody>
                    {this.state.orders.map(order =>
                         <OrderDisplayInTable key={order.activeorderId}
                                              value={order}/>)}
                </tbody>
            </table>
        );
    }
}
