import React, {Component} from 'react';

class OrderDisplayInTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            order: props.value,
            listedCompany: props.listedCompany,
        };
    }

    render() {
        console.log(this.state.order);

        return (
            <>
                <tr className="trade-tr">
                    <td>{this.state.order.body.ticker}</td>
                    <td>{this.state.listedCompany.name}</td>
                    <td>{this.getTypeNameFromTypeId(this.state.order.orderTypeId)}</td>
                    <td>{this.state.order.body.quantity}</td>
                    <td>{this.calculateTotalValue()}</td>
                    <td>{this.state.order.date}</td>
                    <td>{this.renderExecutionType()}</td>
                </tr>
            </>
        );

    }

    getTypeNameFromTypeId(id){
        return id == 1 ?
            "Buy" :
            "Sell";
    }

    calculateTotalValue(){
        return this.state.order.body.executionPrice !== -1 ?
            '~' + (this.state.order.body.quantity * this.state.order.body.executionPrice) :
            'Market';
    }

    renderExecutionType(){
        return this.state.order.body.executionPrice !== -1 ?
            "Limit at " + this.state.order.body.executionPrice + "$" :
            "Market";
    }
}

export default OrderDisplayInTable;
