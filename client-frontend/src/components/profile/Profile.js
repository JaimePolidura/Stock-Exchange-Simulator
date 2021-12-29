import React from "react";
import './Profile.css';
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import auth from "../../services/AuthenticationService";
import socket from "../../services/SocketService";
import listedCompaniesService from "../../data/ListedCompaniesData";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: [],
            cash: profileData.cash,
            orders: [],
            socket: props.value,
        };

        this.setUpSocket();
        this.getTradesFromApi();
        this.getOrdersFromApi();
        this.setUpListedCompanies();
    }

    getTradesFromApi(){
        backendService.getTrades().then(res => {
            this.setState(state => ({...state, trades: res.data.trades}), () => console.log(this.state.trades));
        });
    }

    getOrdersFromApi(){
        backendService.getOrders().then(res => {
            res.data.orders.forEach(order => {
                this.addOrder(order);
            });
        })
    }

    setUpListedCompanies(){
        backendService.getAllListedCompanies().then(res => listedCompaniesService.setListedCompanies(res.data));
    }

    setUpSocket() {
        socket.connect(auth.getUsername());

        socket.onExecutedSellOrder(msg => this.onSellOrderExecuted(msg.body));
        socket.onErrorOrder(msg => this.onErrorOrder(msg.body));
        socket.onExecutedBuyOrder(msg => this.onBuyOrderExecuted(msg.body));
    }

    onBuyOrderExecuted(executedOrder){
        // this.reloadTradesWithTimeout();
        this.getTradesFromApi();
        this.removeOrder(executedOrder);
    }

    onSellOrderExecuted(executedOrder){
        // this.reloadTradesWithTimeout();
        this.getTradesFromApi();
        this.removeOrder(executedOrder);
    }

    addTrade(trade){
        let tradeArray = this.state.trades;
        tradeArray.push(trade);

        this.setState({
            trades: tradeArray,
        });
    }

    removeOrder(orderToRemove){
        let allOrders = this.state.orders;

        let orderFound = allOrders.find(order => order.orderId == orderToRemove.orderId);
        let orderFoundIndex = allOrders.findIndex(order => order.orderId == orderToRemove.orderId);

        if(orderFound.quantity == orderToRemove.quantity){
            allOrders.splice(orderFoundIndex,1);
        }else{
            orderFound.quantity = orderFound.quantity - orderToRemove.quantity;
        }

        this.setState({orders: allOrders});
    }

    onErrorOrder(body){
        let allOrders = this.state.orders;
        let orderToRemoveIndex = allOrders.findIndex(order => order.orderId == body.orderId);

        allOrders.splice(orderToRemoveIndex, 1);

        this.setState({orders: allOrders});
    }

    onOrderBuySended(order){
        this.addOrder(order);
    }

    onOrderSellSended(order){
        this.addOrder(order);
    }

    addOrder(order){
        let orderArrays = this.state.orders;
        orderArrays.push(order);

        this.setState({
            orders: orderArrays,
        });
    }

    render() {
        return (
            <div class="content div-config-dif-background">
                <Options onOrderBuySended = {order => this.onOrderBuySended(order)}/>
                <hr/>
                <Stats cash = {this.state.cash}/>
                <br/>
                <Trades trades={this.state.trades} key={this.state.trades} onOrderSellSended={order => this.onOrderSellSended(order)} />
                <br/>
                <Orders orders={this.state.orders}/>
            </div>
        );
    }
}


export default Profile;