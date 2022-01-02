import {io} from "socket.io-client";

const URL = 'ws://localhost:4000';
const EXECUTED_BUY_ORDER = "order-executed-buy";
const EXECUTED_SELL_ORDER = "order-executed-sell";
const ERROR_ORDER = "order-error";

class ClientEventDispatcherSocketService {

    constructor() {
        this.socket = null;
        this.onExecutedSellOrderCallback = null;
        this.onExecutedBuyOrderCallback = null;
        this.onErrorOrderCallback = null;
    }

    connect(clientId){
        this.socket = io(URL, { transports : ['websocket'] }).on(clientId, msg => {
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
}

export default new ClientEventDispatcherSocketService();