import {io} from "socket.io-client";

const URL = 'ws://localhost:4000';
const EXECUTED_ORDER = "ORDER.EXECUTED";
const ERROR_ORDER = "ORDER.ERROR";

class ClientEventDispatcherSocketService {

    constructor() {
        this.socket = null;
        this.onExecutedOderCallback = null;
        this.onErrorOderCallback = null;
    }

    connect(clientId){
        this.socket = io(URL, { transports : ['websocket'] }).on(clientId, msg => {
            this.#onNewMessage(msg);
        });
    }

    #onNewMessage(msg){
        if(msg.type == EXECUTED_ORDER){
            this.onExecutedOderCallback(msg);
        }else if(msg.type == ERROR_ORDER){
            this.onErrorOderCallback(msg);
        }

    }

    onExecutedOrder(callback){
        this.onExecutedOderCallback = callback;
    }

}

export default new ClientEventDispatcherSocketService();