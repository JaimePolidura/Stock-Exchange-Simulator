import React from "react";
import './Profile.css';
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import auth from "../../services/AuthenticationService";
import socket from "../../services/SocketService";
import listedCompaniesService from "../../services/ListedCompaniesService";

class Profile extends React.Component {
    constructor(props) {
        super(props);

        let profileData = backendService.getProfielData();
        this.state = {
            trades: profileData.trades,
            cash: profileData.cash,
            orders: [],
            socket: props.value,
        };

        this.getOrdersFromApi();
        this.setUpListedCompanies();
        this.setUpSocket();
    }

    getOrdersFromApi(){
        backendService.getOrders().then(res => {
            res.data.orders.forEach(order => {
                this.addOrder(order);
            });
        })
    }

    setUpListedCompanies(){
        backendService.getAllListedCompanies()
            .then(res => listedCompaniesService.setListedCompanies(res.data));
    }

    setUpSocket() {
        socket.connect(auth.getUsername());

        socket.onExecutedOrder(msg => this.onOrderExecuted(msg.body));
        socket.onErrorOrder(msg => this.onErrorOrder(msg.body));
    }

    onOrderExecuted(executedOrder){
        this.removeOrder(executedOrder);

        if(executedOrder.type === 'BUY')
            this.onBuyOrderExecuted(executedOrder);
        else
            this.modifyOrRemoveTrade(executedOrder);
    }

    onBuyOrderExecuted(executedOrder){
        console.log("buyorder");

        let tradeFoundIndex = this.state.trades
            .findIndex(trade => trade.ticker == executedOrder.ticker);

        if(tradeFoundIndex == -1){
            this.addTrade(executedOrder);
        }else{
            this.modifyTrade(executedOrder);
        }
    }

    addTrade(order){
        let listedCompanieData = listedCompaniesService.getListedCompany(order.ticker);
        let allTrades = this.state.trades;

        allTrades.push({
            tradeId: this.creatUUID(),
            ticker: order.ticker,
            name: listedCompanieData.name,
            averagePrice: order.executionPrice,
            actualPrice: order.executionPrice,
            quantity: order.quantity,
            currency: {
                code: listedCompanieData.currencyCode,
                symbol: listedCompanieData.currencySymbol,
            }
        });

        this.setState({trades: allTrades});
    }

    modifyTrade(executedOrder){
        console.log("modifyTrade")
        let allTrades = this.state.trades;
        let tradeFound = allTrades.find(trade => trade.ticker == executedOrder.ticker);

        console.log(JSON.stringify(executedOrder));
        console.log(JSON.stringify(tradeFound));

        tradeFound.averagePrice = Math.round((tradeFound.averagePrice * tradeFound.quantity) + (executedOrder.executionPrice * executedOrder.quantity)
            / (executedOrder.quantity + tradeFound.quantity));
        tradeFound.quantity = executedOrder.quantity + tradeFound.quantity;
        tradeFound.actualPrice = executedOrder.executionPrice;

        this.setState({trades: allTrades});
    }

    removeOrder(orderToRemove){
        let allOrders = this.state.orders;

        let orderFound = allOrders.find(order => order.orderId == orderToRemove.orderId);
        let orderFoundIndex = allOrders.findIndex(order => order.orderId == orderToRemove.orderId);

        console.log(JSON.stringify(orderFound));
        console.log(JSON.stringify(orderToRemove));

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
    }

    modifyOrRemoveTrade(order){
        console.log("sellorder");

        let allTrades = this.state.trades;
        let tradeForThatOrder = allTrades.find(trade => trade.ticker == order.ticker);
        let indexTradeForThatOrder = allTrades.findIndex(trade => trade.ticker == order.ticker);

        console.log(JSON.stringify(order));
        console.log(JSON.stringify((tradeForThatOrder)));

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

    creatUUID(){
        let dt = new Date().getTime();
        let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            let r = (dt + Math.random()*16)%16 | 0;
            dt = Math.floor(dt/16);
            return (c=='x' ? r :(r&0x3|0x8)).toString(16);
        });
        return uuid;
    }
}


export default Profile;