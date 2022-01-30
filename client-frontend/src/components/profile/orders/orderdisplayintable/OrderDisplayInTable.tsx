import React, {Component} from 'react';
import CancelOrderModal from "./cancelordermodal/CancelOrderModal";
import {ExecutionOrder} from "../../../../model/orders/ExecutionOrder";
import {ListedCompany} from "../../../../model/listedcomapnies/ListedCompany";

class OrderDisplayInTable extends React.Component<OrderDisplayInTableProps, OrderDisplayInTableStatus> {
    constructor(props: OrderDisplayInTableProps) {
        super(props);

        this.state = {
            order: props.value,
            listedCompany: props.listedCompany,
            showModal: false,
        };
    }

    render(): any {
        return (
            <>
                <tr className="trade-tr" onClick={() => {this.openModal()}}>
                    <td>{this.state.order.ticker}</td>
                    <td>{this.state.listedCompany.name}</td>
                    <td>{this.state.order.pendingOrderType}</td>
                    <td>{this.state.order.quantity}</td>
                    <td>{this.calculateTotalValue()}</td>
                    <td>{this.state.order.date}</td>
                    <td>{this.renderExecutionType()}</td>
                </tr>
                {this.renderCancelOrderModal()}
            </>
        );

    }

    renderCancelOrderModal(): any{
        return (
            <CancelOrderModal showModal={this.state.showModal}
                              onHide={() => this.closeModal()}
                              order = {this.state.order}
            />
        );
    }

    calculateTotalValue(): string{
        return this.state.order.priceToExecute !== -1 ?
            '~' + (this.state.order.quantity * this.state.order.priceToExecute) :
            'Market';
    }

    renderExecutionType(): string{
        return this.state.order.priceToExecute !== -1 ?
            "Limit at " + this.state.order.priceToExecute + "$" :
            "Market";
    }

    openModal(): void{
        this.setState({showModal: true});
    }

    closeModal(): void{
        this.setState({showModal: false});
    }
}

export default OrderDisplayInTable;

export interface OrderDisplayInTableStatus {
    order: ExecutionOrder,
    listedCompany: ListedCompany,
    showModal: boolean,
}

export interface OrderDisplayInTableProps {
    value: ExecutionOrder,
    listedCompany: ListedCompany,
}