import React from "react";
import './Profile.css';
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
        this.modifyOrRemoveTrade(order);
    }

    modifyOrRemoveTrade(order){
        let allTrades = this.state.trades;
        let tradeForThatOrder = allTrades.find(trade => trade.ticker == order.ticker);
        let indexTradeForThatOrder = allTrades.findIndex(trade => trade.ticker == order.ticker);

        console.log(tradeForThatOrder.quantity);
        console.log(order.quantity);

        if(order.quantity >= tradeForThatOrder.quantity){
            allTrades.splice(indexTradeForThatOrder, 1);
        }else{
            tradeForThatOrder.quantity = tradeForThatOrder.quantity - order.quantity;
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
