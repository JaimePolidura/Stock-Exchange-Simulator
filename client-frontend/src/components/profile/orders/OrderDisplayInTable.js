import React, {Component} from 'react';

class OrderDisplayInTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            order: props.value,
        };
    }

    render() {
        return (
            <>
                <tr className="trade-tr">
                    <td>{this.state.order.ticker}</td>
                    <td>{this.state.order.name}</td>
                    <td>{this.state.order.type}</td>
                    <td>{this.state.order.quantity}</td>
                    <td>{this.renderTotalValue()}</td>
                    <td>{this.state.order.date}</td>
                    <td>{this.state.order.status}</td>
                    <td>{this.renderExecutionType()}</td>
                </tr>
            </>
        );

    }

    renderTotalValue(){
        let order = this.state.order;

        return this.calculateTotalValue();
    }

    calculateTotalValue(){
        let order = this.state.order;

        return order.executionPrice =! -1 ?
            '~' + order.quantity * order.executionPrice :
            'Market';
    }

    renderExecutionType(){
        return this.state.order.executionPrice != -1 ?
            "Limit at " + this.state.order.executionPrice + " " + this.state.order.currency.symbol :
            "Market";
    }
}

export default OrderDisplayInTable;
