import React from "react";
import './Profile.css';
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import auth from "../../services/AuthenticationService";
import socket from "../../services/SocketService";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: [],
            cash: profileData.cash,
            orders: [],
            socket: props.value,
            listedCompanies: [],
            listedCompaniesLoaded: false,
            ordersLoaded: false,
        };

        this.setUpListedCompanies();
        this.setUpSocket();
        this.getOpenPositionsFromApi();
        this.getOrdersFromApi();
    }

    getOpenPositionsFromApi() {
        backendService.getOpenPositions().then(res => {
            this.setState({trades: []}, () => {
                this.setState({trades: this.state.trades.concat(res.data.trades)});
            });
        });
    }

    getOrdersFromApi(){
        backendService.getBuyOrders().then(buyOrdersRes => {
            backendService.getSellOrders().then(sellOrdersRes => {
                let buyOrders = [...buyOrdersRes.data.orders].map(order => {return {...order, type: 'Buy'}});
                let sellOrders = [...sellOrdersRes.data.orders].map(order => {return {...order, type: 'Sell'}});

                let allOrders = buyOrders.concat(sellOrders);

                this.setState({orders: allOrders}, () => {
                    this.setState({ordersLoaded: true});
                });
            });
        });
    }

    setUpListedCompanies(){
        backendService.getAllListedCompanies().then(res => {
            this.setState({listedCompanies: []}, () => {
                this.setState({listedCompanies: this.state.listedCompanies.concat(res.data.allListedCompanies)}, () => {
                    this.setState({listedCompaniesLoaded: true});
                });
            });
        });
    }

    setUpSocket() {
        socket.connect(auth.getUsername());

        socket.onExecutedSellOrder(msg => this.onSellOrderExecuted(msg.body));
        socket.onErrorOrder(msg => this.onErrorOrder(msg.body));
        socket.onExecutedBuyOrder(msg => this.onBuyOrderExecuted(msg.body));
        socket.onOrderCancelled(msg => this.onOrderCancelled(msg.body));
    }

    onBuyOrderExecuted(executedOrder){
        this.getOpenPositionsFromApi();
        this.removeOrderOrDecreaseQuantity(executedOrder);
    }

    onSellOrderExecuted(executedOrder){
        this.getOpenPositionsFromApi();
        this.removeOrderOrDecreaseQuantity(executedOrder);
    }

    onOrderCancelled(orderCancelled){
        let allOrders = this.state.orders;
        let orderFoundIndex = allOrders.findIndex(order => order.orderId == orderCancelled.orderIdCancelled);
        allOrders.splice(orderFoundIndex,1);

        this.setState({orders: allOrders});
    }

    addTrade(trade){
        let tradeArray = this.state.trades;
        tradeArray.push(trade);

        this.setState({
            trades: tradeArray,
        });
    }
    
    removeOrderOrDecreaseQuantity(orderToRemove){
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

                <Stats cash = {this.state.cash} />
                <br/>
                {this.state.listedCompaniesLoaded == true &&
                    <Trades trades={this.state.trades}
                            key={this.state.trades}
                            listedCompanies={this.state.listedCompanies}
                            onOrderSellSended={order => this.onOrderSellSended(order)}/>
                }
                <br />

                {this.state.listedCompaniesLoaded == true && this.state.ordersLoaded == true &&
                    <Orders listedCompanies={this.state.listedCompanies}
                            orders={this.state.orders}/>
                }

                <br/>
            </div>
        );
    }
}


export default Profile;