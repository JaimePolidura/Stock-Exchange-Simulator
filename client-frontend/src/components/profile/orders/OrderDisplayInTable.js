import React, {Component} from 'react';
import CancelOrderModal from "./CancelOrderModal";

class OrderDisplayInTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            order: props.value,
            listedCompany: props.listedCompany,
            showModal: false,
        };
    }

    render() {
        return (
            <>
                <tr className="trade-tr" onClick={() => {this.openModal()}}>
                    <td>{this.state.order.ticker}</td>
                    <td>{this.state.listedCompany.name}</td>
                    <td>{this.state.order.type}</td>
                    <td>{this.state.order.quantity}</td>
                    <td>{this.calculateTotalValue()}</td>
                    <td>{this.state.order.date}</td>
                    <td>{this.renderExecutionType()}</td>
                </tr>
                {this.renderCancelOrderModal()}
            </>
        );

    }

    renderCancelOrderModal(){
        return (
            <CancelOrderModal showModal={this.state.showModal}
                              onHide={() => this.closeModal()}
                              order = {this.state.order}
            />
        );
    }

    getTypeNameFromType(name){
        return name == "BUY" ? "Buy" : "Sell";
    }

    calculateTotalValue(){
        return this.state.order.executionPrice !== -1 ?
            '~' + (this.state.order.quantity * this.state.order.executionPrice) :
            'Market';
    }

    renderExecutionType(){
        return this.state.order.executionPrice !== -1 ?
            "Limit at " + this.state.order.executionPrice + "$" :
            "Market";
    }

    openModal(){
        this.setState({showModal: true});
    }

    closeModal(){
        this.setState({showModal: false});
    }
}

export default OrderDisplayInTable;
