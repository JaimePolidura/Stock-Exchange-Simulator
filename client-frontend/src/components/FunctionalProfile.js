import React, {useEffect, useState} from "react";
import backendService from "../services/BackendService";
import listedCompaniesService from "../data/ListedCompaniesData";
import socket from "../services/SocketService";
import auth from "../services/AuthenticationService";
import Options from "./profile/options/Options";
import Stats from "./profile/stats/Stats";
import Trades from "./profile/trades/Trades";
import Orders from "./profile/orders/Orders";
import FunctionalTrades from "./profile/trades/FunctionalTrades";

const FunctionalProfile = props => {
    const [trades, setTrades] = useState([]);
    const [cash, setCash] = useState(100);
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        backendService.getTrades().then(res => {
            setTrades(res.data.trades);
        });

        setUpSocket();
        // getTradesFromApi();
        getOrdersFromApi();
        setUpListedCompanies();
    }, []);

    const getTradesFromApi = () => {

    }

    const getOrdersFromApi = () => {
        backendService.getOrders().then(res => {
            setOrders(res.data.orders);
        });
    }

    const setUpListedCompanies = () => {
        backendService.getAllListedCompanies()
            .then(res => listedCompaniesService.setListedCompanies(res.data));
    }

    const setUpSocket = () => {
        socket.connect(auth.getUsername());

        socket.onExecutedSellOrder(msg => onSellOrderExecuted(msg.body));
        socket.onErrorOrder(msg => onErrorOrder(msg.body));
        socket.onExecutedBuyOrder(msg => onBuyOrderExecuted(msg.body));
    }

    const onBuyOrderExecuted = (executedOrder) => {
        // this.reloadTradesWithTimeout();
        getTradesFromApi();
        removeOrder(executedOrder);
    }

    const onSellOrderExecuted = (executedOrder) => {
        // this.reloadTradesWithTimeout();
        getTradesFromApi();
        removeOrder(executedOrder);
    }

    const removeOrder = (orderToRemove) => {
        let allOrders = orders.slice();

        let orderFound = allOrders.find(order => order.orderId == orderToRemove.orderId);
        let orderFoundIndex = allOrders.findIndex(order => order.orderId == orderToRemove.orderId);

        if(orderFound.quantity == orderToRemove.quantity){
            allOrders.splice(orderFoundIndex,1);
        }else{
            orderFound.quantity = orderFound.quantity - orderToRemove.quantity;
        }

        setOrders(allOrders);
    }

    const onErrorOrder = (body) => {
        let allOrders = orders.slice();
        let orderToRemoveIndex = allOrders.findIndex(order => order.orderId == body.orderId);

        allOrders.splice(orderToRemoveIndex, 1);

        setOrders(allOrders);
    }

    const onOrderBuySended = (order) => {
        addOrder(order);
    }

    const onOrderSellSended = (order) => {
        addOrder(order);
    }

    const addOrder = order => {
        let orderArrays = orders.slice();
        orderArrays.push(order);

        setOrders(orderArrays);
    }

    return (
        <div class="content div-config-dif-background">
            <Options onOrderBuySended = {order => onOrderBuySended(order)}/>
            <hr/>
            <Stats cash={cash}/>
            <br/>
            <FunctionalTrades trades={trades}/>
            <br/>
            <Orders orders={orders}/>
        </div>
    );
}

export default FunctionalProfile;