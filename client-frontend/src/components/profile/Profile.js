import React from "react";
import './Profile.css';
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import {io} from "socket.io-client";
import auth from "../../services/AuthenticationService";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: profileData.trades,
            cash: profileData.cash,
            orders: [],
        };

        this.getOrdersFromApi();
        this.setUpSocket();
    }

    getOrdersFromApi(){
        backendService.getOrders().then(res => {
            this.addActiveOrdersFromBackend(res);
        })
    }

    addActiveOrdersFromBackend(activeOrders){
        activeOrders.forEach(order => {
            this.addActiveOrderFromBackend(order);
        });
    }

    addActiveOrderFromBackend(order){
        this.addOrder(order);
    }

    setUpSocket(){
        const socket = io('http://localhost:4000', { transports : ['websocket'] });

        socket.on(auth.getUsername(),msg => {
            console.log(msg);
        })
    }

    render() {
        return (
            <div class="content div-config-dif-background">
                <Options onOrderBuySended = {order => this.onOrderBuySended(order)}/>
                <hr/>
                <Stats cash = {this.state.cash}/>
                <br/>
                <Trades trades={this.state.trades}
                        onOrderSellSended={order => this.onOrderSellSended(order)} />
                <br/>
                <Orders orders={this.state.orders}/>
            </div>
        );
    }

    onOrderBuySended(order){
        this.addOrder(order);
    }

    onOrderSellSended(order){
        this.addOrder(order);
        this.modifyOrRemoveTrade(order);
    }

    modifyOrRemoveTrade(order){
        let allTrades = this.state.trades;
        let tradeForThatOrder = allTrades.find(trade => trade.ticker == order.ticker);
        let indexTradeForThatOrder = allTrades.findIndex(trade => trade.ticker == order.ticker);

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