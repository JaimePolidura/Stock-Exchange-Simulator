import {io} from "socket.io-client";
import auth from "./AuthenticationService";

const URL = 'ws://localhost:4000';
const EXECUTED_BUY_ORDER = "order-executed-buy";
const EXECUTED_SELL_ORDER = "order-executed-sell";
const ERROR_ORDER = "order-error";
const ORDER_CANCELLED = "order-cancelled";

class ClientEventDispatcherSocketService {
    constructor() {
        this.socket = null;
        this.onExecutedSellOrderCallback = null;
        this.onExecutedBuyOrderCallback = null;
        this.onErrorOrderCallback = null;
        this.onOrderCancelledCallback = null;
    }

    connect(clientId){
        this.socket = io(URL, {
            transports : ['websocket'],
            auth: {username: auth.username, token: auth.token},
        }).on(clientId, msg => {
            this.onNewMessage(msg);
        });
    }

    onNewMessage(msg){
        console.log(msg);

        if(msg.name == EXECUTED_SELL_ORDER) {
            this.onExecutedSellOrderCallback(msg);
        }else if(msg.name == EXECUTED_BUY_ORDER){
            this.onExecutedBuyOrderCallback(msg);
        }else if(msg.name == ERROR_ORDER){
            this.onErrorOrderCallback(msg);
        }else if(msg.name == ORDER_CANCELLED){
            this.onOrderCancelledCallback(msg);
        }
    }

    onExecutedSellOrder(callback){
        this.onExecutedSellOrderCallback = callback;
    }

    onExecutedBuyOrder(callback){
        this.onExecutedBuyOrderCallback = callback;
    }

    onErrorOrder(callback){
        this.onErrorOrderCallback = callback;
    }

    onOrderCancelled(callback){
        this.onOrderCancelledCallback = callback;
    }
}

export default new ClientEventDispatcherSocketService();