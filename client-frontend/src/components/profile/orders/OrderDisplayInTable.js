import React, {Component} from 'react';
import SellStockModal from "../trades/SellStockModal";
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
                    <td>{this.getTypeNameFromTypeId(this.state.order.orderTypeId)}</td>
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

    getTypeNameFromTypeId(id){
        return id == 1 ? "Buy" : "Sell";
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
