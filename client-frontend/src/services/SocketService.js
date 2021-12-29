import {io} from "socket.io-client";

const URL = 'ws://localhost:4000';
const EXECUTED_BUY_ORDER = "ORDER.BUY.EXECUTED";
const EXECUTED_SELL_ORDER = "ORDER.SELL.EXECUTED";

const ERROR_ORDER = "ORDER.ERROR";

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

        if(msg.type == EXECUTED_SELL_ORDER) {
            this.onExecutedSellOrderCallback(msg);
        }else if(msg.type == EXECUTED_BUY_ORDER){
            this.onExecutedBuyOrderCallback(msg);
        }else if(msg.type == ERROR_ORDER){
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