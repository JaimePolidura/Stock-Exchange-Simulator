import auth from "../AuthenticationService";

const URL = 'ws://localhost/socket';
const EXECUTED_BUY_ORDER = "order-executed-buy";
const EXECUTED_SELL_ORDER = "order-executed-sell";
const ERROR_ORDER = "order-error";
const ORDER_CANCELLED = "order-cancelled";

class ClientEventDispatcherSocketService {
    socket: any;
    onExecutedSellOrderCallback: any;
    onExecutedBuyOrderCallback: any;
    onErrorOrderCallback: any;
    onOrderCancelledCallback: any;

    connect(clientId: string): void{
        let source = new EventSource(`http://localhost/socket/events?username=${auth.username}&token=${auth.token}`);
        source.onmessage = message => {
            console.log("HOla " + message.data);
        };
    }

    onNewMessage(msg: any): void{
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

    onExecutedSellOrder(callback: (msg: any) => void): void{
        this.onExecutedSellOrderCallback = callback;
    }

    onExecutedBuyOrder(callback: (msg: any) => void): void{
        this.onExecutedBuyOrderCallback = callback;
    }

    onErrorOrder(callback: (msg: any) => void): void{
        this.onErrorOrderCallback = callback;
    }

    onOrderCancelled(callback: (msg: any) => void): void{
        this.onOrderCancelledCallback = callback;
    }
}

export default new ClientEventDispatcherSocketService();