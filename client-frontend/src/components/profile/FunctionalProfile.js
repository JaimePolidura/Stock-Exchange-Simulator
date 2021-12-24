import React, {useState, useEffect} from "react";
import './Profile.css';
import backendService from "../../services/BackendService";
import Trades from "./trades/Trades";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import {io} from "socket.io-client";
import auth from "../../services/AuthenticationService";

const FunctionalProfile = () => {
    const [trades, setTrades] = useState([]);
    const [cash, setCash] = useState(0);
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const profileData = backendService.getProfielData();
        setCash(profileData.cash);
        setTrades(profileData.trades);

        backendService.getOrders().then(res => {
            res.forEach(order => {
                this.addOrder(order);
            });
        });

        io('ws://localhost:4000', { transports : ['websocket'] }).on(auth.getUsername(), msg => {
            console.log(msg);
        });
    });

    const onOrderBuySended = order =>{
        addOrder(order);
    }

    const onOrderSellSended = order => {
        addOrder(order);
        modifyOrRemoveTrade(order);
    }

    const modifyOrRemoveTrade = order => {
        let tradeForThatOrder = trades.find(trade => trade.ticker == order.ticker);
        let indexTradeForThatOrder = trades.findIndex(trade => trade.ticker == order.ticker);

        if(order.quantity >= tradeForThatOrder.quantity){
            orders.splice(indexTradeForThatOrder, 1);
        }else{
            tradeForThatOrder.quantity = tradeForThatOrder.quantity - order.quantity;
        }

        setTrades(trades);
    }

    const addOrder = order => {
        orders.push(order);

        setOrders(orders);
    }

    return (
            <div class="content div-config-dif-background">
                <Options onOrderBuySended = {order => onOrderBuySended(order)}/>
                <hr/>
                <Stats cash = {cash}/>
                <br/>
                <Trades trades={trades} onOrderSellSended={order => onOrderSellSended(order)} />
                <br/>
                <Orders orders={orders}/>
            </div>
    );
}


export default FunctionalProfile;