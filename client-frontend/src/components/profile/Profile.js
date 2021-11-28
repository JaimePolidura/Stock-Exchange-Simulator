import React from "react";
import './Profile.css';
import {Trades as trades} from "./Trades";
import TradeDisplayInTable from "./TradeDisplayInTable";
import auth from "../../services/AuthenticationService";

class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            trades: trades,
        };
    }

    render() {
        return (
            <div class="content div-config-dif-background">
                {this.displayHeader()}
                <hr/>
                {this.displayTrades()}
            </div>
        );
    }

    displayTrades() {
        return (
            <table class="display-trades-table">
                <thead>
                <tr>
                    <th>Ticker</th>
                    <th>Name</th>
                    <th>Average Price</th>
                    <th>Actual price</th>
                    <th>Quantity</th>
                    <th>Market value</th>
                    <th>Result</th>
                    <th>Result %</th>
                </tr>
                </thead>

                <tbody>
                {trades.map(trade => <TradeDisplayInTable key={trade.name} value={trade}/>)}
                </tbody>
            </table>
        );
    }

    displayHeader() {
        return (
            <div className="header">
                <div className="header-utils">
                    <div className="header-utils-trade">
                        <button className="btn btn-primary">Trade</button>
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
        );
    }

    logout(){
        auth.logout();
        this.props.history.push("/");
    }
}


export default Profile;
