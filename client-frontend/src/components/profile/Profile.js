import React from "react";
import './Profile.css';
import auth from "../../services/AuthenticationService";
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: profileData.trades,
            cash: profileData.cash,
            orders: [],
        };
    }

    render() {
        return (
            <div class="content div-config-dif-background">
                <Options/>
                <hr/>
                <Stats cash = {this.state.cash}/>
                <br/>
                <Trades trades={this.state.trades} onOrderSellSended={order => this.onOrderSellSended(order)} />
                <br/>
                <Orders orders={this.state.orders}/>
            </div>
        );
    }

    onOrderSellSended(order){
        this.addOrder(order);
    }

    modifyOrRemoveTrade(order){
        let allTrades = [this.state.trades];
        let tradeForThatOrder = allTrades.find(trade => trade.ticker == order.ticker);
        let indexTradeForThatOrder = allTrades.findIndex(trade => trade.ticker == order.ticker);

        if(tradeForThatOrder.quantity >= order.quantity){
            allTrades.slice(indexTradeForThatOrder);
        }else{
            tradeForThatOrder.quantity = tradeForThatOrder.quantity - order.quantity;
            allTrades.slice(indexTradeForThatOrder);
            allTrades.push(tradeForThatOrder);
        }

        this.setState({trades: allTrades});
    }


    addOrder(order){
        let orderArrays = this.state.orders;
        orderArrays.push(order);

        this.setState({
            orders: orderArrays,
        });
    }
}


export default Profile;
