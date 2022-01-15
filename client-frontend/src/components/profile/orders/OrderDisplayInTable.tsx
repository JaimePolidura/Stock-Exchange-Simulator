import React, {Component} from 'react';
import CancelOrderModal from "./CancelOrderModal";

class OrderDisplayInTable extends React.Component<any, any> {
    constructor(props: any) {
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
        return this.state.order.executionPrice !== -1 ?
            '~' + (this.state.order.quantity * this.state.order.executionPrice) :
            'Market';
    }

    renderExecutionType(): string{
        return this.state.order.executionPrice !== -1 ?
            "Limit at " + this.state.order.executionPrice + "$" :
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
