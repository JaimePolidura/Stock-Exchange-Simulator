import React from "react";
import './Options.css';
import auth from "../../../services/AuthenticationService";
import BuyStockModal from "./BuyStockModal";

export default class Options extends React.Component<any, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            showBuyStockModal: false,
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
                            <button className="btn btn-light">Settings</button>
                        </div>
                    </div>
                    <div className="header-logout">
                        <button className="btn btn-danger" onClick={() => this.logout()}>Logout</button>
                    </div>
                </div>

                {this.renderBuyStockModal()}
            </>
        );
    }

    renderBuyStockModal(){
        return <BuyStockModal onOrderBuySended={(order: any) => this.onOrderBuySended(order)}
                              showModal={this.state.showBuyStockModal}
                              onHide={() => this.closeBuyStockModal()}/>
    }

    onOrderBuySended(order: any): void{
        this.props.onOrderBuySended(order);
        this.closeBuyStockModal();
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
