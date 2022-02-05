import React, {ReactElement} from "react";
import './Profile.css';
import backendService from "../../services/api/BackendService";
import OpenPositions from "./openpositions/OpenPositions";
import Stats from "./stats/Stats";
import Options from "./options/Options";
import Orders from "./orders/Orders";
import auth from "../../services/AuthenticationService";
import socket from "../../services/api/SocketService";
import {OpenPosition} from "../../model/positions/OpenPosition";
import {ExecutionOrder} from "../../model/orders/ExecutionOrder";
import {ListedCompany} from "../../model/listedcomapnies/ListedCompany";
import {BuyOrder} from "../../model/orders/BuyOrder";
import {SellOrder} from "../../model/orders/SellOrder";
import {ExecutedOrderType} from "../../model/positions/ExecutedOrderType";

class Profile extends React.Component<{}, ProfileState> {
    constructor(props: any) {
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
            openPositionsLoaded: false,
        };

        this.setUpListedCompanies();
        this.setUpSocket();
        this.getOpenPositionsFromApi();
        this.getOrdersFromApi();
    }

    getOpenPositionsFromApi(): void {
        this.setState({openPositionsLoaded: false});

        backendService.getOpenPositions().then(res => {
            this.setState({trades: []}, () => {
                this.setState({trades: this.state.trades.concat(res.data.openPositions)}, () => {
                    this.setState({openPositionsLoaded: true});
                });
            });
        });
    }

    getOrdersFromApi(): void {
        backendService.getPendingOrders().then(pendingOrdersRes => {
            let allOrders = [...pendingOrdersRes.data.orders];

            this.setState({orders: allOrders}, () => {
                this.setState({ordersLoaded: true});
            });
        });
    }

    setUpListedCompanies(): void {
        backendService.getAllListedCompanies().then(res => {
            this.setState({listedCompanies: []}, () => {
                this.setState({listedCompanies: this.state.listedCompanies.concat(res.data.allListedCompanies)}, () => {
                    this.setState({listedCompaniesLoaded: true});
                });
            });
        });
    }

    setUpSocket(): void {
        socket.connect(auth.getUsername());

        socket.onExecutedSellOrder((msg: any) => this.onSellOrderExecuted(msg.body));
        socket.onErrorOrder((msg: any) => this.onErrorOrder(msg.body));
        socket.onExecutedBuyOrder((msg: any) => this.onBuyOrderExecuted(msg.body));
        socket.onOrderCancelled((msg: any) => this.onOrderCancelled(msg.body));
    }

    onBuyOrderExecuted(executedOrder: any): void{
        setTimeout(() =>{
            this.addOpenPosition(executedOrder);
            this.removeOrderOrDecreaseQuantity(executedOrder);
        }, 100);
    }

    addOpenPosition(executedOrder: any): void {
        let newOpenPosition: OpenPosition = {
            orderId: executedOrder.newPositionId,
            ticker: executedOrder.ticker,
            executedOrderType: ExecutedOrderType.OPEN,
            openingDate: executedOrder.date,
            openingPrice: executedOrder.priceToExecute,
            quantity: executedOrder.quantity,
        };

        this.addTrade(newOpenPosition);
    }

    onSellOrderExecuted(executedOrder: any): void{
        setTimeout(() => {
            this.getOpenPositionsFromApi();
            this.removeOrderOrDecreaseQuantity(executedOrder);
        }, 100);
    }

    onOrderCancelled(orderCancelled: any): void{
        let allOrders = this.state.orders;
        let orderFoundIndex = allOrders.findIndex((order: ExecutionOrder) => order.orderId == orderCancelled.orderIdCancelled);
        allOrders.splice(orderFoundIndex,1);

        this.setState({orders: allOrders});
    }

    addTrade(trade: any): void{
        let tradeArray = this.state.trades;
        tradeArray.push(trade);

        this.setState({
            trades: tradeArray,
        });
    }

    removeOrderOrDecreaseQuantity(orderToRemove: any): void{
        let allOrders = this.state.orders;

        let orderFound = allOrders.find((order: ExecutionOrder) => order.orderId == orderToRemove.orderId);
        let orderFoundIndex = allOrders.findIndex((order: ExecutionOrder) => order.orderId == orderToRemove.orderId);

        if(orderFound == undefined){
            return;
        }

        if(orderFound.quantity == orderToRemove.quantity){
            allOrders.splice(orderFoundIndex,1);
        }else{
            orderFound.quantity = orderFound.quantity - orderToRemove.quantity;
        }

        this.setState({orders: allOrders});
    }

    onErrorOrder(body: any): void{
        let allOrders = this.state.orders;
        let orderToRemoveIndex = allOrders.findIndex((order: any) => order.orderId == body.orderId);

        allOrders.splice(orderToRemoveIndex, 1);

        this.setState({orders: allOrders});
    }

    onOrderBuySended(order: BuyOrder): void{
        this.addOrder(order);
    }

    onOrderSellSended(order: SellOrder): void{
        this.addOrder(order);
    }

    addOrder(order: ExecutionOrder): void{
        let orderArrays = this.state.orders;
        orderArrays.push(order);

        this.setState({
            orders: orderArrays,
        });
    }

    render(): ReactElement {
        return (
            <div className="content div-config-dif-background">
                <Options onOrderBuySended = {(order: any) => this.onOrderBuySended(order)}/>
                <hr/>

                <Stats cash = {this.state.cash} />
                <br/>
                {this.state.listedCompaniesLoaded && this.state.openPositionsLoaded &&
                    <OpenPositions trades={this.state.trades}
                                   listedCompanies={this.state.listedCompanies}
                                   onOrderSellSended={(order: any) => this.onOrderSellSended(order)}/>
                }
                <br />

                {this.state.listedCompaniesLoaded && this.state.ordersLoaded &&
                    <Orders listedCompanies={this.state.listedCompanies}
                            orders={this.state.orders}/>
                }

                <br/>
            </div>
        );
    }
}

export default Profile;

export interface ProfileState {
    trades: OpenPosition[],
    cash: number,
    orders: ExecutionOrder[],
    socket: any,
    listedCompanies: ListedCompany[],
    listedCompaniesLoaded: boolean,
    ordersLoaded: boolean,
    openPositionsLoaded: boolean,
}