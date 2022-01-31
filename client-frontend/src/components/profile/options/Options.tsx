import React from "react";
import './Options.css';
import auth from "../../../services/AuthenticationService";
import BuyStockModal from "./buystockmodal/BuyStockModal";
import ReportModal from "./report/ReportModal";
import {BuyOrder} from "../../../model/orders/BuyOrder";

export default class Options extends React.Component<any, OptionsState> {
    constructor(props: any) {
        super(props);

        this.state = {
            showBuyStockModal: false,
            showReportModal: false,
        };
    }

    render() {
        return (
            <>
                <div className="header">
                    <div className="header-utils">
                        <div className="header-utils-trade">
                            <button className="btn btn-primary" onClick={() => this.showBuyStockModal()}>Trade</button>
                        </div>
                        <div className="header-utils-utils">
                            <button className="btn btn-light">Deposit</button>
                            <button className="btn btn-light">Withdraw</button>
                            <button className="btn btn-light" onClick={() => this.showReportModal()}>Reports</button>
                            <button className="btn btn-light">Settings</button>
                        </div>
                    </div>
                    <div className="header-logout">
                        <button className="btn btn-danger" onClick={() => this.logout()}>Logout</button>
                    </div>
                </div>

                {this.renderTaxesModal()}
                {this.renderBuyStockModal()}
            </>
        );
    }

    renderBuyStockModal(){
        return <BuyStockModal onOrderBuySended={(order: BuyOrder) => this.onOrderBuySended(order)}
                              showModal={this.state.showBuyStockModal}
                              onHide={() => this.closeBuyStockModal()}/>
    }

    renderTaxesModal(){
        return <ReportModal showModal={this.state.showReportModal}
                            onHide={() => this.hideReportModal()}/>
    }

    onOrderBuySended(order: BuyOrder): void{
        this.props.onOrderBuySended(order);
        this.closeBuyStockModal();
    }

    showReportModal(): void {
        this.setState({showReportModal: true});
    }

    hideReportModal(): void {
        this.setState({showReportModal: false});
    }

    showBuyStockModal(): void{
        this.setState({showBuyStockModal: true});
    }

    closeBuyStockModal(): void{
        this.setState({showBuyStockModal: false});
    }

    logout(): void{
        auth.logout();
        this.props.history.push("/");
    }
}

export interface OptionsState {
    showBuyStockModal: boolean,
    showReportModal: boolean,
}
