import React from "react";
import './Profile.css';
import {Trades as trades} from "./Trades";
import TradeDisplayInTable from "./TradeDisplayInTable";

class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.setState({
           trades: trades,
        });
    }

    render(){
        return(
            <div class="content div-config-dif-background">
                {this.displayHeader()}
                <hr/>
                {this.displayTrades()}
            </div>
        );
    }

    displayTrades(){
        return (
            <table>
                <thead>
                    <tr>
                        <th>Ticker</th>
                        <th>Name</th>
                        <th>averagePrice</th>
                        <th>actualPrice</th>
                        <th>quantity</th>
                    </tr>
                </thead>

                <tbody>
                    {trades.map(trade => {
                        <tr>
                            <TradeDisplayInTable key={trade.tradeId} value={trade}/>
                        </tr>
                    })}
                </tbody>
            </table>
        );
    }

    displayHeader(){
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
                    <button className="btn btn-danger">Logout</button>
                </div>
            </div>
        );
    }
}

export default Profile;
